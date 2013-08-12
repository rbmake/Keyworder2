package dao;


import java.util.ArrayList;
import java.sql.*;
import db.*;

import objects.Domain;
import objects.User;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author robb
 */
public class DomainDAO
{
    
    /** Creates a new instance of OrderDAO */
    public DomainDAO()
    {
        
    }
    
    private static Log log = LogFactory.getLog(DomainDAO.class);
    
    private static String GET_MAIN_COMPETITORS =
            "SELECT DISTINCT(RANKING.URL), COUNT(*) FROM RANKING, REQUEST " +
            " WHERE RANKING.REQUEST_ID=REQUEST.ID AND REQUEST.DOMAIN_ID=?" +
            " GROUP BY URL ORDER BY COUNT(*) DESC";

    private static String GET_MAIN_COMPETITORS_BY_REQUEST_ID =
            "SELECT DISTINCT(RANKING.URL), COUNT(*) FROM RANKING, REQUEST " +
            " WHERE RANKING.REQUEST_ID=REQUEST.ID AND REQUEST.DOMAIN_ID=? AND REQUEST.ID=?" +
            " GROUP BY URL ORDER BY COUNT(*) DESC";

    private static String GET_MAIN_COMPETITORS_BY_MONTH_YEAR =
            "SELECT DISTINCT(URL), COUNT(*) NUMBER FROM RANKING WHERE MONTH(TIMESTAMP)=?" +
            " AND YEAR(TIMESTAMP)=? AND RANKING<=10 and RANKING!=0 GROUP BY URL ORDER BY NUMBER ASC";

    private static String GET_DOMAIN_ID = "SELECT ID FROM DOMAIN WHERE NAME=?";

    private static String GET_DOMAIN_FROM_ID = "SELECT ID, NAME FROM DOMAIN WHERE ID=?";

    private static String CAN_USER_SEE_DOMAIN = "SELECT COUNT(*) FROM USER_DOMAIN" +
            " WHERE USER_ID=? and DOMAIN_ID=?";

    private static String GET_USER_DOMAINS = "SELECT DOMAIN.ID,DOMAIN.NAME FROM" +
            " DOMAIN, USER_DOMAIN WHERE USER_DOMAIN.USER_ID=? AND DOMAIN.ID=USER_DOMAIN.DOMAIN_ID";

    public static String ADD_DOMAIN = "INSERT INTO DOMAIN (NAME) VALUES (?)";

    public static String ADD_USER_DOMAIN = "INSERT INTO USER_DOMAIN (DOMAIN_ID, USER_ID) VALUES (?, ?)";


    /**
     * Adds a new domain
     * @param domain
     * @return domainId (Integer)
     */
    public static int addDomain(String domain)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();

        int domainId = 0;
        try
        {
            ps = db.getPreparedStatement(ADD_DOMAIN);

            ps.setString(1, domain);

            ps.execute();

            rs = ps.getGeneratedKeys();
            while (rs.next())
            {
                domainId=rs.getInt(1);
            }

            if (domainId==0)
            {
                log.error("Error adding new domain " + domain);
            }

            rs.close();



            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            log.error(sqle.getMessage());

        }
        catch (Exception e)
        {
            log.error(e.getMessage());
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
                log.error(npe.getMessage());
            }

            return domainId;
        }

    }

    public static int addUserDomain(Domain domain, User user)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();

        int userDomainId = 0;
        try
        {
            ps = db.getPreparedStatement(ADD_USER_DOMAIN);

            ps.setInt(1, domain.getId());
            ps.setInt(2, user.getId());

            ps.execute();

            rs = ps.getGeneratedKeys();
            while (rs.next())
            {
                userDomainId=rs.getInt(1);
            }

            if (userDomainId==0)
            {
                log.error("Error adding new user_domain with user id " + user.getId() + " and domain with id " + domain.getId());
            }

            rs.close();



            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            log.error(sqle.getMessage());

        }
        catch (Exception e)
        {
            log.error(e.getMessage());
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
                log.error(npe.getMessage());
            }

            return userDomainId;
        }

    }

    public static ArrayList getMainCompetitorsByRequestId(int domainId, int requestId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();

        ArrayList domains = new ArrayList();

        try
        {
            //log.info("About to build statement");
            Domain d = new Domain();
            ps = db.getPreparedStatement(GET_MAIN_COMPETITORS);
            // Here set any variables in prepared statement
            ps.setInt(1, domainId);
            //log.info("About to execute query");
            rs = ps.executeQuery();
            //log.info("Executed query");
            while (rs.next())
            {
                d.setId(rs.getInt(1));
                d.setName(rs.getString(2));
                domains.add(d);
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();

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
                    catch( SQLException sqle )
                    {
                        sqle.printStackTrace();
                    }
                }

                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException sqle )
                    {
                        sqle.printStackTrace();
                    }
                }
                db.destroy();

            }
            catch (Exception npe)
            {
                npe.printStackTrace();
            }

            return domains;
        }

    }

    public static ArrayList getMainCompetitors(int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();
        
        ArrayList domains = new ArrayList();

        try
        {
            //log.info("About to build statement");
            Domain d = new Domain();
            ps = db.getPreparedStatement(GET_MAIN_COMPETITORS);
            // Here set any variables in prepared statement
            ps.setInt(1, domainId);
            //log.info("About to execute query");
            rs = ps.executeQuery();
            //log.info("Executed query");
            while (rs.next())
            {
                d.setId(rs.getInt(1));
                d.setName(rs.getString(2));
                domains.add(d);
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();

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
                    catch( SQLException sqle )
                    {
                        sqle.printStackTrace();
                    }
                }

                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException sqle )
                    {
                        sqle.printStackTrace();
                    }
                }
                db.destroy();

            }
            catch (Exception npe)
            {
                npe.printStackTrace();
            }

            return domains;
        }

    }

    public static ArrayList getMainCompetitorsByMonth(int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();

        ArrayList domains = new ArrayList();

        try
        {
            //log.info("About to build statement");
            Domain d = new Domain();
            ps = db.getPreparedStatement(GET_MAIN_COMPETITORS);
            // Here set any variables in prepared statement
            ps.setInt(1, domainId);
            //log.info("About to execute query");
            rs = ps.executeQuery();
            //log.info("Executed query");
            while (rs.next())
            {
                d.setId(rs.getInt(1));
                d.setName(rs.getString(2));
                domains.add(d);
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();

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
                    catch( SQLException sqle )
                    {
                        sqle.printStackTrace();
                    }
                }

                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException sqle )
                    {
                        sqle.printStackTrace();
                    }
                }
                db.destroy();

            }
            catch (Exception npe)
            {
                npe.printStackTrace();
            }

            return domains;
        }

    }

    public static int getDomainId(String domain)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();
        //DBConnectionConnection thisConnection = new DBConnectionConnection();
        int id = 0;

        try
        {
            //log.info("About to build statement");
            
            ps = db.getPreparedStatement(GET_DOMAIN_ID);
            // Here set any variables in prepared statement
            ps.setString(1, domain);
            //log.info("About to execute query");
            rs = ps.executeQuery();
            //log.info("Executed query");
            while (rs.next())
            {
                id = rs.getInt(1);
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
            
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
                    catch( SQLException sqle )
                    {
                        sqle.printStackTrace();
                    }
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
            
            return id;
        }
        
    }

     public static Domain getDomainFromId(int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();
        //DBConnectionConnection thisConnection = new DBConnectionConnection();
        Domain d = new Domain();

        try
        {
            //log.info("About to build statement");

            ps = db.getPreparedStatement(GET_DOMAIN_FROM_ID);
            // Here set any variables in prepared statement
            ps.setInt(1, domainId);
            //log.info("About to execute query");
            rs = ps.executeQuery();
            //log.info("Executed query");
            while (rs.next())
            {
                d.setId(rs.getInt(1));
                d.setName(rs.getString(2));
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();

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
                    catch( SQLException sqle )
                    {
                        sqle.printStackTrace();
                    }
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

            return d;
        }

    }

    public static ArrayList getUserDomains(int userId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();
        //DBConnectionConnection thisConnection = new DBConnectionConnection();
        int id = 0;
        ArrayList domains = new ArrayList();

        try
        {
            //log.info("About to build statement");
            
            ps = db.getPreparedStatement(GET_USER_DOMAINS);
            // Here set any variables in prepared statement
            ps.setInt(1, userId);
            //log.info("About to execute query");
            rs = ps.executeQuery();
            //log.info("Executed query");
            while (rs.next())
            {
                Domain d = new Domain();
                d.setId(rs.getInt(1));
                d.setName(rs.getString(2));
                domains.add(d);
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();

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
                    catch( SQLException sqle )
                    {
                        sqle.printStackTrace();
                    }
                }

                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException sqle )
                    {
                        sqle.printStackTrace();
                    }
                }
                db.destroy();

            }
            catch (Exception npe)
            {
                npe.printStackTrace();
            }

            return domains;
        }

    }


    public static boolean canUserSeeDomain(int userId, int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();
        //DBConnectionConnection thisConnection = new DBConnectionConnection();
        int count = 0;

        try
        {
            //log.info("About to build statement");

            ps = db.getPreparedStatement(CAN_USER_SEE_DOMAIN);
            // Here set any variables in prepared statement
            ps.setInt(1, userId);
            ps.setInt(2, domainId);
            //log.info("About to execute query");
            rs = ps.executeQuery();
            //log.info("Executed query");
            while (rs.next())
            {
                count = rs.getInt(1);
                log.debug("Count = " + count);
            }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();

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
                    catch( SQLException sqle )
                    {
                        sqle.printStackTrace();
                    }
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

            if (count==0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }
   
    /*
    public static void updateOrderAndPaymentStatus(SimpleOrder so)
    {
        PreparedStatement ps = null;
        DBConnectionConnection thisConnection = new DBConnectionConnection();
        
        try
        {
            ps = thisConnection.getPreparedStatement(MGSQL.UPDATE_PAYMENT_AND_ORDER_STATUS);
            ps.setInt(1, so.getOrderStatus().getId());
            ps.setInt(2, so.getPaymentStatus().getId());
            ps.setLong(3,so.getId().longValue());
            ps.executeUpdate();
            
            ps.close();
            ps = null;
            thisConnection.destroy();
        }
        catch (SQLException sqe)
        {
            log.error(sqe.getMessage());
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
                
                thisConnection.destroy();
            }
            catch (Exception npe)
            {
                log.error(npe.getMessage());
            }
        }
    }

    */
}