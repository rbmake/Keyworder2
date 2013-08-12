package actions;

/*
 * LoginAction.java
 *
 * Created on 25 October 2006, 21:31
 *
 * Called to enter the secure area of the site
 */



//J2EE imports
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;

// Imports
import forms.LoginForm;
import objects.User;
import objects.Message;
import objects.Props;
import constants.Constants;
import exceptions.GeneralException;
import logic.UserLogic;
import logic.SessionLogic;
import logic.DomainLogic;

/**
 *
 * @author robb
 */
public class LoginAction extends Action {


    private static Log log = LogFactory.getLog(LoginAction.class);

         
    /**
     * Action called to logon user to secure area
     * <p>
     * Receives username (email) and password from LoginForm and checks them.
     * If they match then it will set the logged on status of the user to true
     * and add that user to the session.
     *
     * @param mapping a mapping from the struts_config.xml
     * @param form the form that was specified in the struts_config.xml 
     * @param request
     * @param response
     * @return the ActionForward object saying where to go to
     */
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        String email;
        String enteredPassword;
        LoginForm lf = (LoginForm)form;
        email = lf.getEmail();
        enteredPassword = lf.getPassword();
        User u = new User();
        
        // First check that both fields are not empty. This should be done by validator
        if ((email!=null) && !(email.equals("")) && (enteredPassword!=null) && !(enteredPassword.equals("")))
        {
            // Now try to get the user by email address
            // Catches UserNotFound or MoreThanOneUser in UserManipulation
            try
            {
                u = UserLogic.getUserByEmail(email);
            }
            catch (GeneralException ge)
            {        
                Message em = ge.getEm();
                request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, em);
                return mapping.findForward("failure");
            }
            
            // Now as long as user is not null, check the password ignoring case
            if ((u!=null) && (enteredPassword.equalsIgnoreCase(u.getPassword())))
            {
                //Set to logged on
                u.setIsLoggedOn(true);

                // Add the user to session
                UserLogic.addUser(request,u);

                log.debug("Logged in user: " + email + ". Id is " + u.getId());

                ArrayList domains = DomainLogic.getUserDomains(u);

                SessionLogic.addObject(request, Constants.SESSION_DOMAINS, domains);
                
                return mapping.findForward("success");

                //return new ActionForward((String)SessionLogic.getSessionObject(request,Constants.REQUESTED_PAGE) + ".do");
            }
            else
            {
                Message em = new Message(Props.getProperty(Constants.KEY_ERROR_PASSWORD_WRONG));
                request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, em);
                return mapping.findForward("failure");
            }
        }
        else
        // Did not enter email and password
        {
            return mapping.findForward("failure");
        }
        
        
    }
}
