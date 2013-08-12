package dao;

/*
 * leywordDAO.java
 */


import java.sql.*;
import db.*;
import objects.*;
import constants.Constants;
import exceptions.GeneralException;
import java.util.ArrayList;
import java.util.Iterator;
import rb.utils.tools.DateTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author robb
 */
public class KeyphraseDAO
{
    
    private static Log log = LogFactory.getLog(KeyphraseDAO.class);
    
    private static String INSERT_KEYPHRASE = "INSERT INTO KEYPHRASE" +
            " (PHRASE, DOMAIN_ID, DO_SEARCH)" +
            " VALUES (?,?,?)";

    private static String GET_ACTIVE_KEYPHRASES_FOR_DOMAIN = "SELECT KEYPHRASE.ID, PHRASE, DO_SEARCH FROM KEYPHRASE, DOMAIN WHERE " +
            "DOMAIN.ID=KEYPHRASE.DOMAIN_ID AND DO_SEARCH=true AND DOMAIN.ID=?" +
            " ORDER BY PHRASE ASC";

    private static String GET_KEYPHRASES_FOR_DOMAIN = "SELECT KEYPHRASE.ID, PHRASE, DO_SEARCH FROM KEYPHRASE, DOMAIN WHERE " +
            "DOMAIN.ID=KEYPHRASE.DOMAIN_ID AND DOMAIN.ID=?" +
            " ORDER BY PHRASE ASC";

    private static String GET_KEYPHRASE_BY_ID = "SELECT ID, PHRASE, DO_SEARCH, DOMAIN_ID FROM KEYPHRASE WHERE ID=?";

    private static String GET_KEYPHRASE_FOR_DOMAIN = "SELECT KEYPHRASE.ID, PHRASE FROM KEYPHRASE WHERE DOMAIN_ID=? AND LOWER(PHRASE)=?";

    private static String DEACTIVATE_ALL_KEYPHRASES = "UPDATE KEYPHRASE SET DO_SEARCH=false WHERE DOMAIN_ID=?";

    private static String SET_ACTIVE_KEYPHRASES = "UPDATE KEYPHRASE SET DO_SEARCH=true WHERE DOMAIN_ID=? AND ID IN";

    private static String DELETE_KEYPHRASE = "DELETE FROM KEYPHRASE WHERE ID=?";

    public static long insertKeyphrase(String keyphrase, int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        
        long keyphraseId = 0;
        try
        {
            ps = db.getPreparedStatement(INSERT_KEYPHRASE);
            ps.setString(1, keyphrase);
            ps.setInt(2, domainId);
            ps.setBoolean(3, true);
            ps.execute();

            rs = ps.getGeneratedKeys();

            keyphraseId = rs.getLong(1);

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
            
            return keyphraseId;
        }
        
    }

    public static int deleteKeyphrase(Integer keyphraseId)
    {
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        int returnCode = Constants.SUCCESS;

        try
        {
            ps = db.getPreparedStatement(DELETE_KEYPHRASE);
            ps.setInt(1, keyphraseId.intValue());

            ps.execute();

            ps.close();
            ps = null;
            db.destroy();
        }
        catch (SQLException sqle)
        {
            log.error(sqle.getMessage());
            returnCode = Constants.FAILURE;

        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
            returnCode = Constants.FAILURE;
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
                log.error(npe.getMessage());
            }

            return returnCode;
        }
    }

    
    public static int deactivateAllKeyphrases(int domainId)
    {
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        int returnCode = 0;
        long keyphraseId = 0;
        try
        {
            ps = db.getPreparedStatement(DEACTIVATE_ALL_KEYPHRASES);
           
            ps.setInt(1, domainId);
            
            returnCode = ps.executeUpdate();

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
            
            return returnCode;
        }
        
    }

     public static int setActiveKeyphrases(int domainId, ArrayList activeKeyphrases)
    {
        Iterator i = activeKeyphrases.iterator();
        String inString = "(";
        while (i.hasNext())
        {
            inString+=(Integer)i.next();
            if (i.hasNext())
            {
                inString+=",";
            }
            else
            {
                inString+=")";
            }
        }

        log.debug("inString is " + inString);

        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        int returnCode = 0;
        try
        {
            ps = db.getPreparedStatement(SET_ACTIVE_KEYPHRASES + inString);

            ps.setInt(1, domainId);

            returnCode = ps.executeUpdate();

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

            return returnCode;
        }

    }
        


     /**
     * Returns a list of Keyword objects for a domain
     * @param domain
     * @return
     */
    public static ArrayList getKeyphrasesForDomain(int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();
        //DBConnectionConnection thisConnection = new DBConnectionConnection();
        ArrayList info = new ArrayList();
        try
        {
            //log.info("About to build statement");

            ps = db.getPreparedStatement(GET_KEYPHRASES_FOR_DOMAIN);
            // Here set any variables in prepared statement
            ps.setInt(1, domainId);
            log.debug("About to execute query");
            rs = ps.executeQuery();
            log.debug("Executed query");
            while (rs.next())
            {
                Keyphrase k = new Keyphrase();
                k.setId(rs.getLong(1));
                k.setName(rs.getString(2));
                k.setDoSearch(rs.getBoolean(3));
                info.add(k);
                log.debug("Added " + k.getName() + " as keyphrase");

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

            return info;
        }

    }


     /**
     * Returns a list of Keyword objects for a domain where the do_search is true
     * @param domain
     * @return
     */
    public static ArrayList getActiveKeyphrasesForDomain(int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();
        //DBConnectionConnection thisConnection = new DBConnectionConnection();
        ArrayList info = new ArrayList();
        try
        {
            //log.info("About to build statement");

            ps = db.getPreparedStatement(GET_ACTIVE_KEYPHRASES_FOR_DOMAIN);
            // Here set any variables in prepared statement
             ps.setInt(1, domainId);
            log.debug("About to execute query");
            rs = ps.executeQuery();
            log.debug("Executed query");
            while (rs.next())
            {
                Keyphrase k = new Keyphrase();
                k.setId(rs.getLong(1));
                k.setName(rs.getString(2));
                k.setDoSearch(rs.getBoolean(3));

                info.add(k);
                log.debug("Added " + k.getName() + " as keyphrase");

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

            return info;
        }

    }


    public static Keyphrase getKeyphraseForDomain(int domainId, String keyphrase)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();
        //DBConnectionConnection thisConnection = new DBConnectionConnection();
        Keyphrase kp = new Keyphrase();
        try
        {
            //log.info("About to build statement");

            ps = db.getPreparedStatement(GET_KEYPHRASE_FOR_DOMAIN);
            // Here set any variables in prepared statement
            ps.setInt(1, domainId);
            ps.setString(2, keyphrase);
            log.debug("About to execute query");
            rs = ps.executeQuery();
            log.debug("Executed query");

            

            while (rs.next())
            {
                
                kp.setId(rs.getLong(1));
                kp.setName(rs.getString(2));
                
                log.debug("Found " + kp.getName() + " as keyphrase");

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

            if (kp==null || kp.getName()==null)
            {
                return null;
            }
            else
            {
                return kp;
            }
        }

    }

    public static Keyphrase getKeyphraseById(long keyphraseId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();
        //DBConnectionConnection thisConnection = new DBConnectionConnection();
        Keyphrase k = new Keyphrase();
        try
        {
            //log.info("About to build statement");

            ps = db.getPreparedStatement(GET_KEYPHRASE_BY_ID);
            // Here set any variables in prepared statement
            ps.setLong(1, keyphraseId);
            log.debug("About to execute query");
            rs = ps.executeQuery();
            log.debug("Executed query");
            while (rs.next())
            {
                
                k.setId(rs.getLong(1));
                k.setName(rs.getString(2));
                k.setDoSearch(rs.getBoolean(3));
                k.setDomain(rs.getInt(4));
                

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

            return k;
        }

    }
   
   

}