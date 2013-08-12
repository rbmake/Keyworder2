package actions;

/*
 * ViewRankingsAction.java
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
import constants.Constants;
import objects.Data;
import objects.Domain;
import objects.User;
import objects.Message;
import objects.MonthYear;
import java.util.ArrayList;
import java.util.Iterator;

// Keyworder Imports


/**
 *
 * @author robb
 */
public class ViewRankingsAction extends Action {



    /**
     * Action called to view rankings for each keyword for a specific domain
     * <p>
     * 
     * @param mapping a mapping from the struts_config.xml
     * @param form the form that was specified in the struts_config.xml 
     * @param request
     * @param response
     * @return the ActionForward object saying where to go to
     */

    private static Log log = LogFactory.getLog(ViewRankingsAction.class);


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        String s = request.getParameter("domainId");
        Domain domain = new Domain();

        if (s!=null)
        {
            try
            {
                int domainId = new Integer(s);
                domain = DomainLogic.getDomainFromId(domainId);
                log.debug("Got domain " + domain.getName());
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
                return mapping.findForward("failure");
            }
        }
        else
        {
            domain=(Domain)request.getSession().getAttribute(Constants.SESSION_DOMAIN);
        }

        String strMonth = request.getParameter("month");
        MonthYear month = new MonthYear();

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
            rankingHistories = RankingLogic.getBestRankingsByMonth(domain.getId(), rankingMonths);
            
            
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

            request.setAttribute(Constants.REQUEST_DOMAIN, domain);
            request.getSession().setAttribute(Constants.SESSION_DOMAIN, domain);
            
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
            request.setAttribute(Constants.REQUEST_RANKING_HISTORIES, rankingHistories);
            request.setAttribute(Constants.REQUEST_RANKING_MONTHS, rankingMonths);
            return mapping.findForward("success");
        }
        
    }
}
