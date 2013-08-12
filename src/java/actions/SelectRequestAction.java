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

import objects.Domain;
import objects.User;
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
public class SelectRequestAction extends Action {



    /**
     * Action called to view requests for a specific domain and select request to view rankings for
     * <p>
     * 
     * @param mapping a mapping from the struts_config.xml
     * @param form the form that was specified in the struts_config.xml 
     * @param request
     * @param response
     * @return the ActionForward object saying where to go to
     */

    private static Log log = LogFactory.getLog(SelectRequestAction.class);


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        String strDomain = request.getParameter("domainId");
    
        Domain domain = new Domain();
        
        
        if (strDomain!=null)
        {
            try
            {
                int domainId = new Integer(strDomain);
                domain = DomainLogic.getDomainFromId(domainId);
                SessionLogic.addObject(request, Constants.SESSION_DOMAIN, domain);
                log.debug("Got domain " + domain.getName());
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
                return mapping.findForward("failure");
            }
        }
        

        User user = UserLogic.getUser(request);

        
        if (user==null)
        {
            return mapping.findForward("login");
        }

        if (DomainLogic.canUserSeeDomain(domain,user))
        {
            log.debug("User can see domain " + domain.getName());

            // Get all requests
            ArrayList requests = DomainLogic.getAllRequestsForDomain(domain);
            SessionLogic.addObject(request, Constants.REQUEST_RANKING_REQUESTS, requests);
            return mapping.findForward("failure");

            
        }
        else
        {
            log.warn("User cannot see domain " + domain.getName());
            Message em = new Message("You are not allowed to view domain " + domain.getName());
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, em);
            return mapping.findForward("failure");
        }

        
    }

   
}
