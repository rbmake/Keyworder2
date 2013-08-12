/*
 * UserDAO.java
 */

package dao;


import objects.User;
import objects.Address;
import exceptions.GeneralException;

import java.sql.*;
import java.util.ArrayList;
import db.DBConnection;
import constants.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author robb
 */
public class UserDAO
{
    
    /** Creates a new instance of customerDAO */
    public UserDAO()
    {
        
    }
    
    private static Log log = LogFactory.getLog(UserDAO.class);

    /********************************************************************
     ***** USERS ********************************************************
     *******************************************************************/
    public static String GET_USERS_BY_SURNAME =
            "SELECT ID," +
            "TITLE_ID," +
            "FIRST_NAME," +
            "SURNAME," +
            "EMAIL, " +
            "PASSWORD," +
            "IS_ACCOUNT_LOCKED," +
            "NUM_LOGON_FAILURES, " +
            "TELEPHONE_NUMBER, " +
            "RECEIVE_EMAIL_NEWSLETTER " +
            "from USER " +
            "WHERE USER.SURNAME LIKE ?";

    public static String GET_USER_BY_EMAIL =
            "SELECT ID," +
            "COOKIE_CODE," +
            "TITLE_ID," +
            "FIRST_NAME," +
            "SURNAME," +
            "EMAIL," +
            "PASSWORD," +
            "TELEPHONE_NUMBER," +
            "IS_ACCOUNT_LOCKED," +
            "NUM_LOGON_FAILURES," +
            "PRIVILEGE_ID," +
            "RECEIVE_EMAIL_NEWSLETTER " +
            "from USER WHERE USER.EMAIL = ?";

    public static String GET_USER_BY_ID =
            "SELECT ID," +
            "COOKIE_CODE," +
            "TITLE_ID," +
            "FIRST_NAME," +
            "SURNAME," +
            "EMAIL," +
            "PASSWORD," +
            "IS_ACCOUNT_LOCKED," +
            "NUM_LOGON_FAILURES " +
            "from USER WHERE ID = ?";
    

     public static String GET_USER_BY_COOKIE_CODE =
            "SELECT ID," +
            "TITLE_ID," +
            "FIRST_NAME," +
            "SURNAME," +
            "EMAIL," +
            "PASSWORD," +
            "IS_ACCOUNT_LOCKED," +
            "NUM_LOGON_FAILURES, " +
            "TELEPHONE_NUMBER " +
            "from USER WHERE COOKIE_CODE = ?";

    // ID, USER_CODE, TITLE_ID, FIRST_NAME, SURNAME, EMAIL, NUM_LOGON_FAILURES
    public static String ADD_USER =
            "INSERT INTO USER (COOKIE_CODE, TITLE_ID, FIRST_NAME, SURNAME, EMAIL," +
            " NUM_LOGON_FAILURES, IS_ACCOUNT_LOCKED, PASSWORD," +
            " TELEPHONE_NUMBER, RECEIVE_EMAIL_NEWSLETTER)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?)";

    public static String UPDATE_USER =
            "UPDATE USER " +
            "SET COOKIE_CODE=?, TITLE_ID=?, FIRST_NAME=?, SURNAME=?, EMAIL=?," +
            " NUM_LOGON_FAILURES=?, IS_ACCOUNT_LOCKED=?, PASSWORD=?," +
            " TELEPHONE_NUMBER=?, RECEIVE_EMAIL_NEWSLETTER=? WHERE ID=?";

    public static String DELETE_USER =
            "DELETE FROM USER WHERE ID = ?";

    /**
     *Gets users by surname. Surname can be a partial since this is a LIKE search
     *@param surname Part surname
     *@returns ArrayList Arraylist of users
     */
    public static ArrayList getUsersBySurname(String surname)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        ArrayList users = new ArrayList();
        
        try
        {
            
            ps = db.getPreparedStatement(GET_USERS_BY_SURNAME);
            ps.setString(1,"%"+surname+"%");
            rs = ps.executeQuery();
            
            while ( rs.next() )
            {
                User c = new User();
                c.setId(rs.getInt(1));
                c.setTitle(rs.getInt(2));
                c.setFirstName(rs.getString(3));
                c.setSurname(rs.getString(4)) ;
                c.setEmail(rs.getString(5));
                c.setPassword(rs.getString(6));
                c.setIsAccountLocked(rs.getBoolean(7));
                c.setNumLogonFailures(rs.getInt(8));
                c.setTelephoneNumber(rs.getString(9));
                c.setReceiveEmailNewsletter(rs.getBoolean(10));
                users.add(c);
            }
            rs.close();
            rs=null;
            ps.close();
            ps = null;
            db.destroy();
        }
        finally
        {
            try
            {
                if( rs != null )
                {
                    try
                    {
                        rs.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                db.destroy();
            }
            catch (Exception npe)
            {
                npe.printStackTrace();
            }
            
            return users;
            
        }
        
        
    }


    
    
    public static User getUserByEmail(String email)
    throws GeneralException
    {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBConnection db = new DBConnection();
        User user = new User();
        int i=0;
        try
        {
            ps = db.getPreparedStatement(GET_USER_BY_EMAIL);
            ps.setString(1,email);
            
            rs = ps.executeQuery();
            
            
            while ( rs.next() )
            {
                
                user.setId(rs.getInt(1));
                user.setCookieCode(rs.getInt(2));
                user.setTitle(rs.getInt(3));
                user.setFirstName(rs.getString(4));
                user.setSurname(rs.getString(5)) ;
                user.setEmail(rs.getString(6));
                user.setPassword(rs.getString(7));
                user.setTelephoneNumber(rs.getString(8));
                user.setIsAccountLocked(rs.getBoolean(9));
                user.setNumLogonFailures(rs.getInt(10));
                user.setPrivilege(rs.getInt(11));
                user.setReceiveEmailNewsletter(rs.getBoolean(12));
                i++;
            }
            rs.close();
            rs=null;
            ps.close();
            ps = null;
            db.destroy();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Fatal error in getUserByEmail. Msg is " + e.getMessage());
        }
        finally
        {
            try
            {
                if( rs != null )
                {
                    try
                    {
                        rs.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                db.destroy();
            }
            catch (Exception npe)
            {
                npe.printStackTrace();
            }
            
            if (i>1)
            {
                
                throw new GeneralException(Errors.MORE_THAN_ONE_USER, i + " users found for email:" + email);
            }
            if (i==0)
            {
                throw new GeneralException(Errors.NO_USER_FOUND, "No users found with email:" + email);
            }
            return user;
        }
    }
    
    public static User getUserById(int userId) throws GeneralException
    {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        DBConnection db = new DBConnection();
        User user = new User();
        
        try
        {
            ps = db.getPreparedStatement(GET_USER_BY_ID);
            ps.setLong(1,userId);
            
            rs = ps.executeQuery();
            
            int i=0;
            while ( rs.next() )
            {
                //System.err.println("Entered result set");
                user.setId(rs.getInt(1));
                user.setCookieCode(rs.getInt(2));
                user.setTitle(rs.getInt(3));
                user.setFirstName(rs.getString(4));
                user.setSurname(rs.getString(5)) ;
                user.setEmail(rs.getString(6));
                user.setPassword(rs.getString(7));
                user.setIsAccountLocked(rs.getBoolean(8));
                user.setNumLogonFailures(rs.getInt(9));
                i++;
            }
            rs.close();
            rs=null;
            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            log.error("SQL Exception:" + sqle.getMessage());
        }
        finally
        {
            try
            {
                if( rs != null )
                {
                    try
                    {
                        rs.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                db.destroy();
            }
            catch (Exception npe)
            {
                npe.printStackTrace();
            }
            return user;
        }
    }
    
   
    
    public static int addNewUser(User u) throws GeneralException
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        int userId = 0;
        try
        {
            ps = db.getPreparedStatement(ADD_USER);
            ps.setInt(1, u.getCookieCode());
            ps.setInt(2, u.getTitle().getId());
            ps.setString(3,u.getFirstName());
            ps.setString(4,u.getSurname());
            ps.setString(5, u.getEmail());
            ps.setInt(6, 0);
            ps.setBoolean(7, false);
            ps.setString(8, u.getPassword());
            ps.setString(9, u.getTelephoneNumber());
            ps.setBoolean(10, u.getReceiveEmailNewsletter());
            ps.execute();
            rs = ps.getGeneratedKeys();
            
            while (rs.next())
            {
                
                userId = rs.getInt(1);
            }
            rs.close();
            rs=null;
            ps.close();
            ps=null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            if (sqle.getErrorCode()==SQLReturnCodes.SQL_DUPLICATE_KEY)
            {
                throw new GeneralException(Errors.DUPLICATE_ENTRY);
            }
            
        }
        finally
        {
            try
            {
                if( rs != null )
                {
                    rs.close();
                }
                if( ps != null )
                {
                    ps.close();
                }
                db.destroy();
            }
            catch( Exception sqle )
            {
                log.error(sqle.getMessage());
            }
        }
        return userId;
        
        
    }
    
    
    public static void deleteUser(Long userId) throws GeneralException
    {
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        
        try
        {
            ps = db.getPreparedStatement(DELETE_USER);
            ps.setLong(1, userId.longValue());
            ps.execute();
            ps.close();
            ps = null;
            db.destroy();
            
        }
        catch (SQLException sqle)
        {
            log.error(sqle.getMessage());
            
        }
        finally
        {
            try
            {
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                db.destroy();
            }
            catch (Exception npe)
            {
                npe.printStackTrace();
            }
            
            
        }
    }
    
    public static int updateUser(User u) throws GeneralException
    {
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        
        try
        {
            ps = db.getPreparedStatement(UPDATE_USER);
            ps.setInt(1, u.getCookieCode());
            ps.setInt(2, u.getTitle().getId());
            ps.setString(3,u.getFirstName());
            ps.setString(4,u.getSurname());
            ps.setString(5, u.getEmail());
            ps.setInt(6, u.getNumLogonFailures());
            ps.setBoolean(7, u.isIsAccountLocked());
            ps.setString(8, u.getPassword());
            ps.setString(9, u.getTelephoneNumber());
            ps.setBoolean(10, u.getReceiveEmailNewsletter());
            ps.setInt(11,u.getId());
            ps.executeUpdate();
            
            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            log.error(sqle.getMessage());
            if (sqle.getErrorCode()==SQLReturnCodes.SQL_DUPLICATE_KEY)
            {
                throw new GeneralException(Errors.DUPLICATE_ENTRY);
            }
        }
        finally
        {
            try
            {
                
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                db.destroy();
            }
            catch (Exception npe)
            {
                npe.printStackTrace();
            }   
        }
        return 0;
    }

    /**
     *Gets users by surname. Surname can be a partial since this is a LIKE search
     *@param surname Part surname
     *@returns ArrayList Arraylist of users
     */
    /*
    public static ArrayList getUsersAndAddressBySurname(String surname)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnectionConnection thisConnection = new DBConnectionConnection();
        ArrayList usersAndAddress = new ArrayList();

        try
        {

            ps = thisConnection.getPreparedStatement(MGSQL.GET_USERS_AND_ADDRESS_BY_SURNAME);
            ps.setString(1,"%"+surname+"%");
            rs = ps.executeQuery();

            while ( rs.next() )
            {
                User c = new User();
                Address a = new Address();
                UserAddress ua = new UserAddress();
                c.setId(rs.getLong(1));
                c.setTitle(rs.getInt(2));
                c.setFirstName(rs.getString(3));
                c.setSurname(rs.getString(4)) ;
                c.setEmail(rs.getString(5));
                c.setPassword(rs.getString(6));
                c.setIsAccountLocked(rs.getBoolean(7));
                c.setNumLogonFailures(rs.getInt(8));
                c.setTelephoneNumber(rs.getString(9));
                c.setReceiveEmailNewsletter(rs.getBoolean(10));
                a.setAddress1(rs.getString(11));
                a.setAddress2(rs.getString(12));
                a.setAddress3(rs.getString(13));
                a.setCity(rs.getString(14));
                a.setPostcode(rs.getString(15));
                a.setCountry(rs.getInt(16));
                ua.setAddress(a);
                ua.setUser(c);
                usersAndAddress.add(ua);
            }
            rs.close();
            rs=null;
            ps.close();
            ps = null;
            thisConnection.destroy();
        }
        finally
        {
            try
            {
                if( rs != null )
                {
                    try
                    {
                        rs.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }

                thisConnection.destroy();
            }
            catch (Exception npe)
            {
                log.error(npe.getMessage());
            }

            return usersAndAddress;

        }


    }

    public static ArrayList getAllUsers()
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnectionConnection thisConnection = new DBConnectionConnection();
        ArrayList users = new ArrayList();

        try
        {

            ps = thisConnection.getPreparedStatement(MGSQL.GET_ALL_USERS);
            rs = ps.executeQuery();

            while ( rs.next() )
            {
                User c = new User();
                c.setId(rs.getLong(1));
                c.setTitle(rs.getInt(2));
                c.setFirstName(rs.getString(3));
                c.setSurname(rs.getString(4)) ;
                c.setEmail(rs.getString(5));
                c.setPassword(rs.getString(6));
                c.setIsAccountLocked(rs.getBoolean(7));
                c.setNumLogonFailures(rs.getInt(8));
                c.setTelephoneNumber(rs.getString(9));
                c.setReceiveEmailNewsletter(rs.getBoolean(10));
                users.add(c);
            }
            rs.close();
            rs=null;
            ps.close();
            ps = null;
            thisConnection.destroy();
        }
        finally
        {
            try
            {
                if( rs != null )
                {
                    try
                    {
                        rs.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }

                thisConnection.destroy();
            }
            catch (Exception npe)
            {
                log.error(npe.getMessage());
            }

            return users;

        }


    }*/
    
}
