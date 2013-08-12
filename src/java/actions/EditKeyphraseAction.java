package actions;

/*
 * EditKeyphraseAction.java
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
import objects.MonthYear;
import java.util.ArrayList;
import java.util.Iterator;

// Keyworder Imports


/**
 *
 * @author robb
 */
public class EditKeyphraseAction extends Action {



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

    private static Log log = LogFactory.getLog(EditKeyphraseAction.class);


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        String s = request.getParameter("keyphraseId");
        Keyphrase kp = new Keyphrase();

        if (s!=null)
        {
            try
            {
                int keyphraseId = new Integer(s);
                kp = KeyphraseLogic.getKeyphraseFromId(keyphraseId);
                log.debug("Got keyphrase " + kp.getName());
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
                return mapping.findForward("failure");
            }
        }
        else
        {
            Message m = new Message("Please select a keyphrase to edit");
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, m);
            return mapping.findForward("failure");
        }
        
        User user = UserLogic.getUser(request);

       

        if (user==null)
        {
            return mapping.findForward("login");
        }
        

        if (DomainLogic.canUserSeeDomain(kp.getDomain(),user))
        {
            // Show UpdateKeyword page
            return mapping.findForward("success");
            
        }
        else
        {
            log.warn("User cannot see domain " + kp.getDomain().getName());
            return mapping.findForward("failure");
        }

    
        
    }
}
