package actions;



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

import logic.SessionLogic;
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
public class ViewSearchResultsAction extends Action {



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

    private static Log log = LogFactory.getLog(ViewSearchResultsAction.class);


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        // Get domain
        Domain domain=(Domain)request.getSession().getAttribute(Constants.SESSION_DOMAIN);

        // Get most recent request id
        Integer requestId = (Integer)SessionLogic.getSessionObject(request, Constants.SESSION_REQUEST_ID);
        
        User user = UserLogic.getUser(request);

        ArrayList rankings = new ArrayList();

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

            rankings = RankingLogic.getAllDomainRankings(domain.getId());


            request.setAttribute(Constants.REQUEST_RANKINGS, domain);
            return mapping.findForward("success");
        }
        else
        {
            Message m = new Message("You do not have rights on this domain");
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, m);
            return mapping.findForward("failure");
        }
        
    }
}
