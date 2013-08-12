/*
 * UserManipulation.java
 *
 *
 */

package logic;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import objects.*;
import constants.*;
import exceptions.GeneralException;

import constants.Constants;
import dao.UserDAO;
//import src.dao.AddressDAO;
import javax.servlet.http.Cookie;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.SQLException;
/**
 *
 * @author robb
 */
public class UserLogic
{
    
    private static Log log = LogFactory.getLog(UserLogic.class);
    /** Creates a new instance of ShoppingBasketManipulation */
    public UserLogic()
    {
    }
    
    /**
     * Adds a user to the session
     * @param request the HttpServletRequest
     * @param u user to be added
     */
    public static void addUser(HttpServletRequest request, User u)
    {
        try
        {
            SessionLogic.addObject(request,Constants.SESSION_USER, u);
        }
        catch (IllegalStateException ise)
        {
            log.error("IllegalStateException. No session for user with email:" + u.getEmail());
        }
    }
    
    public static User getUserById(int userId) throws GeneralException
    {
        return UserDAO.getUserById(userId);
    }
    
    /**
     * Adds a new customer
     *
     * @param u user to be added
     */
    public static User addNewUser(User u) throws GeneralException
    {
        
       int userId = UserDAO.addNewUser(u);
       
       u.setId(userId);
       return u;
       
    }
    
    /**
     * Deletes a customer from the database
     *
     * @param userId user to be added
     */
    public static void deleteUser(Long userId) throws GeneralException
    {
        
       
        UserDAO.deleteUser(userId);

        
        
    }
    
    /**
     * Gets the user from the session if there.
     ** @param request
     * @return User user object from session; if not there returns null
     */
    public static User getUser(HttpServletRequest request)
    {
        User u = null;
        try
        {
            u = (User)request.getSession().getAttribute(Constants.SESSION_USER);
            
            
        }
        catch (IllegalStateException ise)
        {
            log.error("Could not get user session");
        }
        finally
        {
            return u;
        }
        
    }
    
    public static User getUserByEmail(String email) throws GeneralException
    {
        User user = new User();
        user = UserDAO.getUserByEmail(email);
        return user;
        
    }
    
     /**
     *Gets all users and their invoice address for a partial surname
     *
     *@param surname The partial surname to search (can be empty)
     */
    /*public static ArrayList getUsersBySurname(String surname)
    {
        return UserDAO.getUsersAndAddressBySurname(surname);
    }
    
    public static ArrayList getAllUsers()
    {
        return UserDAO.getAllUsers();
    }*/
    
    /**
     * Removes the User object from the session
     */
    public static void removeUser(HttpServletRequest request)
    {
        try
        {
            request.getSession().removeAttribute(Constants.SESSION_USER);
        }
        catch (IllegalStateException ise)
        {
            log.error("Illegal state exception:" + ise.getMessage());
        }
    }
    
    /**
     * Gets User cookie from request if there
     * If there it will get the User details from the database
     * @param request
     * @return User
     */
    /*public static User getUserFromCookie(HttpServletRequest request)
    {
        User u = null;
        Cookie cookies[] = request.getCookies();
        Cookie cookie = null;
        boolean foundCookie = false;
        int i=0;
        
        // Check whether there are any cookies first
        if (cookies!=null)
        {
            while ((foundCookie==false) && (i<cookies.length))
            {
                cookie = cookies[i];
                if (cookie.getName().equalsIgnoreCase(MGConstants.COOKIE_USER))
                {
                    foundCookie = true;
                }
                i++;
            }
        }
        
        
        try
        {
            // Important here to check if the String equals null for the cookie value
            //if ((cookie!=null) && (cookie.getValue()!=null) && (cookie.getValue()!="") && (cookie.getValue()!="null") && (foundCookie==true))
            if ((cookie==null) || (cookie.getValue()==null) || (foundCookie==false) || (cookie.getValue().equals("null")))
            {
                //No user associated with that cookie
            }
            else
            {
                // Get user from DB
                u = UserDAO.getUserByCookieCode(new Integer(cookie.getValue()));
            }
        }
        catch (NumberFormatException nfe)
        {
            log.error("Number format exception: " + nfe.getMessage());
        }
        finally
        {
            return u;
        }
    }
    
    /**
     * Gets user from session and sets loggedIn status to false
     * Adds user back to session
     */
    public static void logOffUser(HttpServletRequest request)
    {
        User u = getUser(request);
        if (u!=null)
        {
            u.setIsLoggedOn(false);
            addUser(request,u);
        }
    }
    
    /**
     * Updates user details
     * @param user
     */
    public static void updateUser(User u) throws GeneralException
    {
        UserDAO.updateUser(u);
        
        
    }
    
    
}
