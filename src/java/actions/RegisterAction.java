/*
 * LoginAction.java
 *
 * Created on 25 October 2006, 21:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package actions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.upload.FormFile;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import forms.RegisterForm;
import exceptions.GeneralException;
import objects.User;
import objects.Message;
import objects.Data;
import objects.Props;
import dao.UserDAO;
import constants.Constants;
import logic.SessionLogic;
import logic.UserLogic;
import javax.servlet.http.Cookie;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author robb
 */
public class RegisterAction extends Action {
        
    private static Log log = LogFactory.getLog(RegisterAction.class);
    
   
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        
        RegisterForm rf = (RegisterForm)form;
        
        // Create new user
        User u = new User();
        // Clear existing user and attributes
        //SessionLogic.removeSecureAttributes(request);
        SessionLogic.removeAttribute(request,Constants.SESSION_USER);
            
        u.setTitle(rf.getTitleId());
        u.setFirstName(rf.getFirstName());
        u.setSurname(rf.getSurname());
        u.setEmail(rf.getRegisterEmail());
        u.setPassword(rf.getRegisterPassword());
        u.setTelephoneNumber(rf.getTelephoneNumber());
        u.setCookieCode(u.getNewCookieCode());
        
        // Check the main required fields are there
        if ((u.getEmail()!=null) && (u.getPassword()!=null))
        {
            try
            {
                u = UserLogic.addNewUser(u);
                // Set the user to logged on so that user can move on through order
                // If this is not set then the user will be asked to log on 
                u.setIsLoggedOn(true);
                // Add the user to session
                UserLogic.addUser(request,u);
            }
            catch (GeneralException ge)
            {
                request.setAttribute(Constants.REQUEST_ERROR_MESSAGE,ge.getEm());
                
                return mapping.findForward("failure");
                
            }
            // Stores the users cookie code on the user's computer
            //Cookie c = new Cookie(Constants.COOKIE_USER, u.getCookieCodeAsString());
            
            // This sets the time the cookie is stored on the PC in seconds
            // We want as long as possible
            // 36,000,000 is 100,000 hours
            //c.setMaxAge(Constants.COOKIE_STORAGE_TIME);
            //response.addCookie(c);
            
        }
        
        String requestedPage = (String)SessionLogic.getSessionObject(request,Constants.REQUESTED_PAGE);
        if (requestedPage!=null)
        {
            requestedPage = requestedPage + ".do";
            return new ActionForward(requestedPage);
        }
        else
        {
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE,Props.getProperty(Constants.DEFAULT_ERROR_MESSAGE_KEY));
            return mapping.findForward("failure");
        }
        
    }
}
