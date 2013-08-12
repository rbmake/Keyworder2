/*
 * SignOutAction.java
 */

package actions;

//J2EE imports
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

// Apache imports
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import logic.UserLogic;
import objects.User;
/**
 *
 * @author robb
 */
public class LogoutAction extends Action {
      
    /** Creates a new instance of SignOutAction */
    public LogoutAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        User u = UserLogic.getUser(request);

        if (u!=null)
        {
            // Remove user from session
             //Set to logged on
            u.setIsLoggedOn(false);

            // Add the user to session
            UserLogic.removeUser(request);
        }
        
        
        return mapping.findForward("success");
    }
}
