/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import dao.DomainDAO;
import dao.RankingDAO;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import objects.Domain;
import objects.User;
import objects.Request;

import rb.utils.tools.DateTool;

/**
 *
 * @author robbrown
 */
public class DomainLogic
{

    private static Log log = LogFactory.getLog(DomainLogic.class);
    /**
     *
     * @param domain
     * @return
     */
    /*public static Domain getDefaultDomain()
    {
        //int domainId = DomainDAO.getDomainId(domain);

        DomainDAO.g
    }*/

    public static Domain getDomainFromName(String domainName)
    {
        int domainId = DomainDAO.getDomainId(domainName);
        Domain d = new Domain();
        d.setId(domainId);
        d.setName(domainName);
        return d;
    }

     public static Domain getDomainFromId(int domainId)
    {
        Domain d = DomainDAO.getDomainFromId(domainId);
        
        return d;
    }

    public static boolean canUserSeeDomain(Domain domain, User user)
    {
        log.debug("About to see if user with id " + user.getId() + " can see domain with id " + domain.getId());
        return DomainDAO.canUserSeeDomain(user.getId(), domain.getId());
    }
    
    public static ArrayList getUserDomains(User user)
    {
        return DomainDAO.getUserDomains(user.getId());
    }

    public static Boolean doesUserAlreadyHaveDomain(String domain, User user)
    {
        ArrayList domains = getUserDomains(user);
        if (domains!=null)
        {
            Iterator i = domains.iterator();
            while (i.hasNext())
            {
                Domain d = (Domain)i.next();
                if (d.getName().equalsIgnoreCase(domain))
                {
                    return true;
                }

            }
            return false;
        }

        return false;
    }

    public static Integer addDomain(String domain, User user)
    {
        int domainId = DomainDAO.addDomain(domain);
        int userDomainId=0;
        if (domainId>0)
        {
            Domain d = new Domain (domainId, domain);
            userDomainId=DomainDAO.addUserDomain(d, user);
        }
        return userDomainId;
    }

   
    /**
     * Returns an ArrayList of Request objects for all ranking requests for this domain
     * @param domain
     * @return
     */
    public static ArrayList getAllRequestsForDomain(Domain domain)
    {
        return RankingDAO.getAllRequestsForDomain(domain.getId());
    }

    /**
     * This method consolidates requests that were done on the same day into one request
     * @param domain
     * @return
     */
    public static ArrayList getConsolidatedRequestsForDomain(Domain domain)
    {
        ArrayList requests = getAllRequestsForDomain(domain);
        ArrayList returnRequests = new ArrayList();
        Iterator i = requests.iterator();
        Request thisRequest = new Request();
        if (i.hasNext())
        {
            thisRequest = (Request) i.next();
        }

        while (i.hasNext())
        {
            Request nextRequest = (Request) i.next();
            if (DateTool.getDateAsString(nextRequest.getDate(), "dd MMM yy").equalsIgnoreCase(DateTool.getDateAsString(thisRequest.getDate(), "dd MMM yy")))
            {
                // Same date so do nothing
            }
            else
            {
               // Different date - add the first request
                returnRequests.add(thisRequest);
            }

            thisRequest=nextRequest;

        }

        // Add the final request
        returnRequests.add(thisRequest);

        return returnRequests;
    }

}
