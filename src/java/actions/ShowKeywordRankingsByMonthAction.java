package actions;

/*
 * 
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

import logic.KeyphraseLogic;
import logic.RankingLogic;
import logic.UserLogic;
import logic.DomainLogic;
import constants.Constants;
import objects.Keyphrase;
import objects.Request;
import objects.User;
import objects.Message;
import objects.MonthYear;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author robb
 */
public class ShowKeywordRankingsByMonthAction extends Action {


    private static Log log = LogFactory.getLog(ShowKeywordRankingsByMonthAction.class);

         
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
       
        /*
        String strKeywordId = request.getParameter("keywordId");
        String strMonthYear = request.getParameter("monthYear");
        int keywordId=0;

        log.debug("Keyword id is " + strKeywordId);
        log.debug("Month year is " + strMonthYear);

        try
        {
            keywordId = new Integer(strKeywordId);
            
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            return mapping.findForward("failure");
        }

       

        User user = UserLogic.getUser(request);

        ArrayList rankings = new ArrayList();

        if (user==null)
        {
            return mapping.findForward("login");
        }

        //if (DomainLogic.canUserSeeDomain(domain,user))
        //{

        rankings=RankingLogic.getRankingsByKeywordAndMonthYear(keywordId, strMonthYear);

        request.setAttribute(Constants.REQUEST_KEYWORD_RANKINGS, rankings);*/

        return mapping.findForward("success");

        
        
    }
}
