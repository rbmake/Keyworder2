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

import logic.SessionLogic;
import logic.RankingLogic;
import logic.UserLogic;
import logic.DomainLogic;
import constants.Constants;

import objects.Domain;
import objects.User;
import objects.Message;
import objects.Request;
import objects.Props;
import objects.MonthYear;
import objects.Data;

import java.util.ArrayList;
import java.util.Collections;

import forms.RequestForm;

// Keyworder Imports


/**
 *
 * @author robb
 */
public class ViewRankingsByRequestAction extends Action {



    /**
     * Action called to view rankings for a specific request for a specific domain
     * <p>
     * 
     * @param mapping a mapping from the struts_config.xml
     * @param form the form that was specified in the struts_config.xml 
     * @param request
     * @param response
     * @return the ActionForward object saying where to go to
     */

    private static Log log = LogFactory.getLog(ViewRankingsByRequestAction.class);


    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
     throws Exception
    {
        // Can either get request from URL or post
        String strDomain = request.getParameter("domainId");
        //String strRequestId = request.getParameter("requestId");
        String strOrderBy = request.getParameter("orderBy");

        // The request that is of interest is passed via the RequestForm
        RequestForm f = (RequestForm)form;
        Request rankingRequest = new Request();
       
        Domain domain = new Domain();
        Long requestId = null;
        Boolean bExport = false;
        ArrayList rankings = new ArrayList();

      
        // get domain from session
        domain = (Domain)SessionLogic.getSessionObject(request, Constants.SESSION_DOMAIN);

        requestId=f.getRequestId();
        String sExport = f.getExport();
        

        try
        {
            bExport = new Boolean(sExport);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            return mapping.findForward("failure");
        }

        User user = UserLogic.getUser(request);

        
        if (user==null)
        {
            return mapping.findForward("login");
        }

        if (domain!=null)
        {
            log.debug("Domain is " + domain.getName());
        }
        else
        {
            Message em = new Message("Domain is null");
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, em);

            return mapping.findForward("failure");
        }

        if (DomainLogic.canUserSeeDomain(domain,user))
        {
            log.debug("User can see domain " + domain.getName());

            

            // Get all the rankings for this request
            if (requestId!=null)
            {
                rankings = RankingLogic.getRankingsByRequest(requestId);

                if (strOrderBy!=null)
                {
                    Collections.sort(rankings);
                }
                else
                {
                    log.debug("No month passed so ordering by keyword");
                }


                rankingRequest = RankingLogic.getRequestById(requestId);
                request.setAttribute(Constants.REQUEST_REQUEST, rankingRequest);
            }
            

            if (rankings!=null)
            {
                log.debug("Retrieved " + rankings.size() + " rankings");
            }
            
        }
        else
        {
            log.warn("User cannot see domain " + domain.getName());
        }

        if (rankings==null || rankings.size()==0)
        {
            log.debug("No rankings found for domain " + domain.getName() + " and request " + requestId);
            Message em = new Message("No rankings found for domain " + domain.getName() + " and request " + requestId);
            request.setAttribute(Constants.REQUEST_ERROR_MESSAGE, em);
            return mapping.findForward("failure");
        }
        else
        {
            
            if (bExport)
            {
                // Create file name
                String appLocation = request.getSession().getServletContext().getRealPath("/");
                String sExportFileName = Props.getProperty(Constants.KEY_EXCEL_OUTPUT_FILE);

                /*(String fileName, String keyColumnHeading, String valueColumnHeading, ArrayList keys,
                String keysClassType, String keysMethodName,
                ArrayList values, String valuesClassType, String valuesMethodName)*/
                rb.utils.tools.FileTool.writeTwoColumnExcelHtmlFileFromArrayList
                        (appLocation + sExportFileName, "Keyword", "Ranking", rankings, "objects.Ranking", "getKeyword.getName",
                        rankings, "objects.Ranking", "getRanking");


                // Now show the recently created PDF
                ActionForward af = new ActionForward (sExportFileName);
                return af;
            }


            request.setAttribute(Constants.REQUEST_RANKINGS, rankings);
            return mapping.findForward("success");
             
        }
        
    }

   
}
