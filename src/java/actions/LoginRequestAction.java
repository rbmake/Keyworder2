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
import javax.servlet.http.Cookie;
import java.util.ArrayList;

// Apache imports
import org.apache.struts.upload.FormFile;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



/**
 *
 * @author robb
 */
public class LoginRequestAction extends Action {
         
    /**
     * Action called to go to log on page.
     * Cannot go directly since in secure area
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
        
            return mapping.findForward("success");
    }
       
}
