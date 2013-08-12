package actions;

/*
 * ViewKeywordsAction.java
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
import logic.UserLogic;
import logic.DomainLogic;
import logic.SessionLogic;
import constants.Constants;
import objects.Keyphrase;
import objects.Domain;
import objects.User;
import objects.Message;
import java.util.ArrayList;

// Keyworder Imports


/**
 *
 * @author robb
 */
public class ViewKeywordsAction extends Action {



    /**
     * Action called to view keywords for a specific domain
     * <p>
     * 
     * @param mapping a mapping from the struts_config.xml
     * @param form the form that was specified in the struts_config.xml 
     * @param request
     * @param response
     * @return the ActionForward object saying where to go to
     */

    private static Log log = LogFactory.getLog(ViewKeywordsAction.class);


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        Domain domain = new Domain();
        domain= (Domain)SessionLogic.getSessionObject(request, Constants.SESSION_DOMAIN);
        if (domain!=null)
        {
            log.debug("Domain is " + domain.getName());
        }
        else
        {
            log.debug("Domain from session is null");
        }


        String s = request.getParameter("domainId");
        
        log.debug("Domain from URL is " + s);
        if (s!=null)
        {
            try
            {
                int domainId = new Integer(s);
                domain = DomainLogic.getDomainFromId(domainId);
                
                SessionLogic.addObject(request, Constants.SESSION_DOMAIN, domain);
                log.debug("Added domain " + ((Domain)SessionLogic.getSessionObject(request, Constants.SESSION_DOMAIN)).getName() + " to session");
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
                return mapping.findForward("failure");
            }
        }

        if (domain==null)
        {
            domain= (Domain)SessionLogic.getSessionObject(request, Constants.SESSION_DOMAIN);
            if (domain!=null)
            {
                log.debug("Domain is " + domain.getName());
            }
            else
            {
                log.debug("Domain from session is null");
            }

        }

        

        SessionLogic.addObject(request, Constants.SESSION_DOMAIN, domain);

        User user = UserLogic.getUser(request);

        ArrayList keyphrases = new ArrayList();

        if (user==null)
        {
            return mapping.findForward("login");
        }

        if (DomainLogic.canUserSeeDomain(domain,user))
        {
            log.debug("User can see domain " + domain.getName());
            keyphrases = KeyphraseLogic.getKeyphrases(domain);
            
            if (keyphrases!=null)
            {
                log.debug("Retrieved " + keyphrases.size() + " keyphrases");
            }
            
        }
        else
        {
            log.warn("User cannot see domain " + domain.getName());
        }

        if (keyphrases==null || keyphrases.size()==0)
        {
            log.debug("No keyphrases found for domain " + domain.getName());
            Message em = new Message("No keyphrases found for domain " + domain.getName());
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, em);
            return mapping.findForward("failure");
        }
        else
        {
            request.setAttribute(Constants.REQUEST_KEYPHRASES, keyphrases);
            return mapping.findForward("success");
        }
        
    }
}
