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

import forms.MountainsForm;

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
public class TestCheckboxAction extends Action {

    private static Log log = LogFactory.getLog(TestCheckboxAction.class);

         
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
        String s = request.getParameter("domainId");
        Domain domain = new Domain();

        if (s!=null)
        {
            try
            {
                int domainId = new Integer(s);
                domain = DomainLogic.getDomainFromId(domainId);
                log.debug("Got domain " + domain.getName());
            }
            catch (Exception e)
            {
                log.error(e.getMessage());
                return mapping.findForward("failure");
            }
        }
        else
        {
            domain=(Domain)request.getSession().getAttribute(Constants.SESSION_DOMAIN);
        }

        MountainsForm f = (MountainsForm)form;
        ArrayList keyphrases = new ArrayList();
        // Each checkbox is called item. The form populates an array of strings via the getKeyphrase method
        // Now run through each item in the array and get the id.
        // This works for checkboxes since the value of the checkbox identifies the row
        // With text boxes its the value of the item you want e.g. quantity=2 so you have no way of
        // identifying the text box
        String[] value = f.getMountains();
        log.debug("Size of array in form is " + value.length);

        if (value!=null)
        {
            Integer keyphraseId = null;
            for (int i=0; i<value.length; i++)
            {
                try
                {
                    keyphraseId = new Integer(value[i]);
                }
                catch (Exception e)
                {
                    // Need to handle situation that not an integer
                }
                finally
                {
                    keyphrases.add(keyphraseId);
                    log.debug("Keyphrase id = " + value[i]);
                }
            }

        }

        if (keyphrases!=null)
        {
            KeyphraseLogic.setActiveKeyphrases(domain, keyphrases);
        }
        

        // Need to get domain request
        User u = UserLogic.getUser(request);
        if ((u.IsLoggedOn()) && (DomainLogic.canUserSeeDomain(domain, u)))
        {
            String browser = "FIREFOX";
            int numResults = 30;
            SearchEngineSetting ses = new SearchEngineSetting();
            ses.setBrowserClient(RankingLogic.getGoogleBrowserString(browser));
            ses.setNumResults(numResults);
            ses.setSearchEngine(Constants.GOOGLE);

            request.getSession().setAttribute(Constants.SESSION_DOMAIN, domain);

            // This starts off a thread that gets all search results for all keyphrases for this domain
            // where do_search=true
            SearchResults sr = new SearchResults(domain, ses);
            

            return mapping.findForward("success");

        }
        
        else
        // Not allowed
        {
            return mapping.findForward("failure");
        }
        
        
    }
}
