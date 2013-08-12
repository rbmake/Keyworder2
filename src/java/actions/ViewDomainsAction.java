package actions;

//J2EE imports
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import objects.User;
import logic.UserLogic;
import logic.DomainLogic;
import logic.SessionLogic;

import constants.Constants;

import java.util.ArrayList;


/**
 *
 * @author robb
 */
public class ViewDomainsAction extends Action {


    private static Log log = LogFactory.getLog(ViewDomainsAction.class);

         
    /**
     * 
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
       
        User user = UserLogic.getUser(request);

        if (user!=null)
        {
            ArrayList domains = DomainLogic.getUserDomains(user);

            SessionLogic.addObject(request, Constants.SESSION_DOMAINS, domains);
            
            return mapping.findForward("success");
        }
        else
        {
            return mapping.findForward("login");
        }
       

        
        
    }
}
