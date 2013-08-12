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

import forms.AddKeyphrasesForm;

import rb.utils.tools.StringTool;

// Keyworder Imports


/**
 *
 * @author robb
 */
public class AddKeyphraseAction extends Action {



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

    private static Log log = LogFactory.getLog(AddKeyphraseAction.class);


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        User user = UserLogic.getUser(request);

        if (user==null)
        {
            return mapping.findForward("login");
        }

        ArrayList rejectedKeyphrases = new ArrayList();
        AddKeyphrasesForm akf = (AddKeyphrasesForm) form;
        String strKeyphrases = akf.getKeyphrases();
        ArrayList keyphrases = StringTool.split(strKeyphrases, ',');

        // Get domain from session
        Domain domain =(Domain)request.getSession().getAttribute(Constants.SESSION_DOMAIN);

        if (!DomainLogic.canUserSeeDomain(domain,user))
        {
            log.warn("User cannot see domain " + domain.getName());
            Message m = new Message("You do not have access rights to this domain");
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, m);
            return mapping.findForward("failure");
        }

        int iCount=0;
        if (keyphrases!=null)
        {
            Iterator i = keyphrases.iterator();
            while (i.hasNext())
            {
                String keyphrase = (String)i.next();

                if (KeyphraseLogic.doesKeyphraseAlreadyExistForDomain(keyphrase, domain))
                {
                    rejectedKeyphrases.add(keyphrase);
                    
                }
                else
                {
                    KeyphraseLogic.addKeyphraseToDomain(domain, keyphrase);
                    iCount++;

                }
            }
        }
        else
        {
            Message m = new Message("Please ensure the keyphrase is not blank");
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, m);
            return mapping.findForward("failure");
        }

        if (rejectedKeyphrases.size()>0)
        {
            request.setAttribute(Constants.REQUEST_REJECTED_KEYPHRASES, rejectedKeyphrases);
        }
        
        request.setAttribute(Constants.REQUEST_NUMBER_KEYPHRASES_ADDED, iCount);
        return mapping.findForward("success");
        
    }
}
