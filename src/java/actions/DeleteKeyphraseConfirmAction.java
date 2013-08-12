package actions;

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

import forms.DeleteKeyphraseForm;

// Keyworder Imports


/**
 *
 * @author robb
 */
public class DeleteKeyphraseConfirmAction extends Action {



    /**
     * Action called to actually delete a keyphrase
     * <p>
     * 
     * @param mapping a mapping from the struts_config.xml
     * @param form the form that was specified in the struts_config.xml 
     * @param request
     * @param response
     * @return the ActionForward object saying where to go to
     */

    private static Log log = LogFactory.getLog(DeleteKeyphraseConfirmAction.class);


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        DeleteKeyphraseForm f = (DeleteKeyphraseForm)form;
        log.debug("YesNo equals " + f.getYesNo());
        log.debug("Keyphrase id is " + f.getId());
        String yesNo = f.getYesNo();
        
        Integer id = f.getId();
        
        if (yesNo.equalsIgnoreCase("Yes"))
        {
            Keyphrase kp = KeyphraseLogic.getKeyphraseFromId(id);
            log.debug("About to delete keyphrase " + kp.getName());
            if (KeyphraseLogic.deleteKeyphrase(id)==Constants.SUCCESS)
            {
                Message m = new Message("Keyphrase " + kp.getName() + " was successfully deleted");
                request.setAttribute(Constants.REQUEST_MESSAGE, m);
            }
            else
            {
                Message m = new Message("There was a failure in deleting keyphrase " + kp.getName());
                request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, m);
            }
            return mapping.findForward("success");
        }
        else
        {
            return mapping.findForward("success");
        }

          
        
    }
}
