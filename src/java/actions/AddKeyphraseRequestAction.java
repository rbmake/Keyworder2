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
public class AddKeyphraseRequestAction extends Action {



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

    private static Log log = LogFactory.getLog(AddKeyphraseRequestAction.class);


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        
        
        
            return mapping.findForward("success");
      
    
        
    }
}
