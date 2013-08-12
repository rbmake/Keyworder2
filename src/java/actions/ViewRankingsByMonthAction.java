package actions;

/*
 * ViewRankingsByMonthAction.java
 */



//J2EE imports
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// Apache imports
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import logic.KeyphraseLogic;
import logic.RankingLogic;
import logic.UserLogic;
import logic.DomainLogic;
import logic.SessionLogic;
import constants.Constants;
import objects.Data;
import objects.Domain;
import objects.User;
import objects.Keyphrase;
import objects.Message;
import objects.MonthYear;
import objects.RankingHistory;
import objects.Ranking;
import objects.Props;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.*;

// Keyworder Imports


/**
 *
 * @author robb
 */
public class ViewRankingsByMonthAction extends Action {



    /**
     * Action called to view rankings in order for a specific month for a specific domain
     * <p>
     * 
     * @param mapping a mapping from the struts_config.xml
     * @param form the form that was specified in the struts_config.xml 
     * @param request
     * @param response
     * @return the ActionForward object saying where to go to
     */

    private static Log log = LogFactory.getLog(ViewRankingsByMonthAction.class);


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        String strDomain = request.getParameter("domainId");
        String strMonth = request.getParameter("month");
        String sExport = request.getParameter("export");
       
        Domain domain = new Domain();
        MonthYear month = new MonthYear();
        Boolean bExport = false;

        try
        {
            int domainId = new Integer(strDomain);
            domain = DomainLogic.getDomainFromId(domainId);
            log.debug("Got domain " + domain.getName());
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            return mapping.findForward("failure");
        }

        if (strMonth!=null)
        {
            try
            {
                int monthId = new Integer(strMonth);
                month = Data.getMonthYear(monthId);
                log.debug("Got month " + month.getName());
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
                return mapping.findForward("failure");
            }
        }

        try
        {
            bExport = new Boolean(sExport);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            return mapping.findForward("failure");
        }

        User user = UserLogic.getUser(request);

        ArrayList rankingHistories = new ArrayList();
        ArrayList rankingMonths = new ArrayList();

        if (user==null)
        {
            return mapping.findForward("login");
        }

        if (DomainLogic.canUserSeeDomain(domain,user))
        {
            log.debug("User can see domain " + domain.getName());
            rankingMonths = RankingLogic.getDomainRankingMonths(domain.getId());

            // This gets an ArrayList of RankingHistory objects
            // Within each RankingHistory objects is a getRankings object which will get an ArrayList of Ranking objects
            // Each Ranking object has a number of attributes including keyphrase, timestamp and ranking
            if (strMonth!=null)
            {
                rankingHistories = RankingLogic.getAllDomainRankingsSortedByMonth(domain.getId(), rankingMonths, month.getId());
            }
            else
            {
                log.debug("No month passed so ordering by keyword");
                rankingHistories = RankingLogic.getBestRankingsByMonth(domain.getId(), rankingMonths);
            
            }

            if (rankingHistories!=null)
            {
                log.debug("Retrieved " + rankingHistories.size() + " rankings");
            }
            if (rankingMonths!=null)
            {
                log.debug("Retrieved " + rankingMonths.size() + " ranking months");
                if (log.isDebugEnabled())
                {
                    Iterator i = rankingMonths.iterator();
                    while (i.hasNext())
                    {
                        MonthYear m = (MonthYear)i.next();
                        log.debug("Month is " + m.getName());
                    }
                }
            }

            SessionLogic.addObject(request, Constants.SESSION_DOMAIN, domain);

            
        }
        else
        {
            log.warn("User cannot see domain " + domain.getName());
        }

        if (rankingHistories==null || rankingHistories.size()==0)
        {
            log.debug("No rankings found for domain " + domain.getName());
            Message em = new Message("No rankings found for domain " + domain.getName());
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, em);
            return mapping.findForward("failure");
        }
        else
        {
            if (bExport)
            {
                String appLocation = request.getSession().getServletContext().getRealPath("/");
                
                String sExportFileName = Props.getProperty(Constants.KEY_EXCEL_OUTPUT_FILE);
                ArrayList keywords = new ArrayList();
                Iterator i = rankingHistories.iterator();
                while (i.hasNext())
                {
                    RankingHistory rh = (RankingHistory)i.next();
                    Keyphrase k = rh.getKeyword();
                    k.setName(k.getSentenceName());
                    keywords.add(k);
                }

                log.debug("About to write excel file with name " + (appLocation + sExportFileName));

                rb.utils.tools.FileTool.writeExcelHtmlFileFromArrayList
                        (appLocation + sExportFileName, rankingHistories, "objects.RankingHistory", "getRankings",
                        "objects.Ranking", "getRanking", "objects.MonthYear", "getName", rankingMonths,
                        "objects.Keyphrase", "getName", keywords);


                // Now show the recently created file
                ActionForward af = new ActionForward ("/" + sExportFileName, true);
                return af;
            }

            log.debug("Setting " + rankingHistories.size() + " keyword histories");
            request.setAttribute(Constants.REQUEST_RANKING_HISTORIES, rankingHistories);
            log.debug("Setting " + rankingMonths.size() + " months");
            request.setAttribute(Constants.REQUEST_RANKING_MONTHS, rankingMonths);
            return mapping.findForward("success");
        }
        
    }

   
}
