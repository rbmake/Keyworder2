package actions;

/*
 * GetSearchResultsAction.java
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

import forms.KeyphraseForm;

import java.util.ArrayList;

// Imports
import objects.SearchEngineSetting;
import objects.User;
import objects.Message;
import objects.Props;
import objects.SearchResults;
import objects.Domain;
import constants.Constants;
import exceptions.GeneralException;
import logic.UserLogic;
import logic.SessionLogic;
import logic.DomainLogic;
import logic.RankingLogic;
import logic.KeyphraseLogic;

/**
 *
 * @author robb
 */
public class SeeTestAction extends Action {

    private static Log log = LogFactory.getLog(SeeTestAction.class);

         
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
        
            return mapping.findForward("success");

       
        
        
    }
}
