package actions;

/*
 * ViewAllRankingsAction.java
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
import objects.Keyphrase;
import objects.Domain;
import objects.User;
import objects.Message;
import objects.RankingHistory;
import objects.Ranking;
import objects.MonthYear;
import java.util.ArrayList;
import java.util.Iterator;

// Keyworder Imports


/**
 *
 * @author robb
 */
public class ViewAllRankingsAction extends Action {



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

    private static Log log = LogFactory.getLog(ViewAllRankingsAction.class);


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
        
        User user = UserLogic.getUser(request);

        ArrayList rankingHistories = new ArrayList();
        ArrayList rankingRequests = new ArrayList();

        if (user==null)
        {
            return mapping.findForward("login");
        }
        if (domain==null)
        {
            Message m = new Message("No domain known");
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, m);
            return mapping.findForward("failure");
        }

        if (DomainLogic.canUserSeeDomain(domain,user))
        {
            log.debug("User can see domain " + domain.getName());

            // This gets an ArrayList of RankingHistory objects. The number should be equal to the number of keyphrases
            // that are set up for this domain and active.
            
            //rankingRequests = DomainLogic.getAllRequestsForDomain(domain);

            // This gets only the requests for each particular day i.e. duplicate requests on same day are ignored
            rankingRequests = DomainLogic.getConsolidatedRequestsForDomain(domain);

            //rankingHistories = RankingLogic.getPaddedAllDomainRankings(domain.getId(), rankingRequests);

            rankingHistories = RankingLogic.getAllDomainRankings(domain.getId());

            if (rankingHistories!=null)
            {
                log.debug("Retrieved " + rankingHistories.size() + " rankings. This should be equal to the number of keyphrase " +
                        "that are set up for this domain and active.");

                if (log.isDebugEnabled())
                {
                    Iterator i = rankingHistories.iterator();
                    while (i.hasNext())
                    {
                        RankingHistory rh = (RankingHistory) i.next();
                        log.debug(rh.getKeyword().getName() + " has the following rankings:");
                        ArrayList al = rh.getRankings();
                        Iterator j = al.iterator();
                        while (j.hasNext())
                        {
                            Ranking r = (Ranking)j.next();
                            log.debug(r.getRanking() + " for request " + r.getRequest().getId());
                        }
                    }
                }
            }
            if (rankingRequests!=null)
            {
                log.debug("Retrieved " + rankingRequests.size() + " ranking requests");
                
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
            request.setAttribute(Constants.REQUEST_RANKING_REQUESTS, rankingRequests);
            return mapping.findForward("success");
        }
        
    }
}
