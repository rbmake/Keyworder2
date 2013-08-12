/*
 * SessionManipulation.java
 *
 * Created on 27 October 2006, 18:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package logic;
import exceptions.GeneralException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import objects.User;

import constants.Constants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


                  
/**
 *
 * @author robb
 */
public class SessionLogic {

    private static Log log = LogFactory.getLog(SessionLogic.class);
    
    public static void addObject(HttpServletRequest request, String sessionAttribute , Object o)
    throws IllegalStateException
    {
        HttpSession session = request.getSession();
        session.setAttribute(sessionAttribute, o);
    }
   
    
    public static void removeAttribute(HttpServletRequest request, String sessionAttribute )
    {
        HttpSession session = request.getSession();
        session.removeAttribute(sessionAttribute);
    }
    
    public static Object getSessionObject(HttpServletRequest request, String attribute)
    {
        Object o = new Object();
        try
        {
            HttpSession session = request.getSession();
            o = session.getAttribute(attribute);
                        
        }
        catch (IllegalStateException ise)
	{
		log.error("No session available");
	}
	finally
        {
            return o;
        }
        
    }
    
    /*
     * Removes orders, delivery and invoice address
     * Note that it currently does not log the user off
     * @param request
     */
    /*public static void removeSecureAttributes(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
       
        session.removeAttribute(Constants.SESSION_DELIVERY_ADDRESS);
        session.removeAttribute(Constants.SESSION_INVOICE_ADDRESS);
        //session.removeAttribute(MGConstants.SESSION_USER_ORDER);
    }*/
   
}
