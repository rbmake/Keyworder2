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

import logic.SessionLogic;
import logic.UserLogic;
import logic.DomainLogic;
import constants.Constants;
import objects.User;
import objects.Message;
import java.util.ArrayList;

import forms.DomainForm;


// Keyworder Imports


/**
 *
 * @author robb
 */
public class AddDomainAction extends Action {



    /**
     * Action called to add a new domain for this user
     * <p>
     * 
     * @param mapping a mapping from the struts_config.xml
     * @param form the form that was specified in the struts_config.xml 
     * @param request
     * @param response
     * @return the ActionForward object saying where to go to
     */

    private static Log log = LogFactory.getLog(AddDomainAction.class);


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        User user = UserLogic.getUser(request);

        if (user==null)
        {
            return mapping.findForward("login");
        }

       
        DomainForm df = (DomainForm) form;
        String strDomain = df.getName();
       
        if (strDomain!=null)
        {
            
            if (DomainLogic.doesUserAlreadyHaveDomain(strDomain, user))
            {
                Message m = new Message("This domain is already present on your account");
                request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, m);
                return mapping.findForward("failure");

            }
            else
            {
                DomainLogic.addDomain(strDomain, user);

            }
            
        }
        else
        {
            Message m = new Message("Please ensure the domain is not blank");
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, m);
            return mapping.findForward("failure");
        }


        ArrayList domains = DomainLogic.getUserDomains(user);

        SessionLogic.addObject(request, Constants.SESSION_DOMAINS, domains);

        return mapping.findForward("success");

    }
}
