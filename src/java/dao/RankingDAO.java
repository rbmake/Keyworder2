package dao;

/*
 * rankingDAO.java
 */


import java.sql.*;
import db.*;
import objects.SearchEngineSetting;
import objects.Ranking;
import objects.Keyphrase;
import objects.Data;
import objects.MonthYear;
import objects.Request;
import java.util.ArrayList;
import java.util.Iterator;
import rb.utils.tools.DateTool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author robb
 */
public class RankingDAO
{

    /** Creates a new instance of OrderDAO */
    public RankingDAO()
    {

    }

    private static Log log = LogFactory.getLog(RankingDAO.class);
    private static String INSERT_RANKING_RESULT = "INSERT INTO RANKING" +
            " (RANKING_HISTORY_ID, KEYPHRASE_ID, URL, RANKING, REQUEST_ID)" +
            " VALUES (?,?,?,?,?)";

    private static String INSERT_RANKING_HISTORY = "INSERT INTO RANKING_HISTORY" +
            " (SEARCH_ENGINE, NUM_RESULTS, LANGUAGE, CLIENT_BROWSER, REQUEST_ID) VALUES (?,?,?,?,?)";

    private static String INSERT_DOMAIN_RANKING = "INSERT INTO DOMAIN_RANKING" +
            " (DOMAIN_ID, KEYPHRASE_ID, RANKING, URL, RANKING_HISTORY_ID, REQUEST_ID) VALUES (?,?,?,?,?,?)";

    private static String GET_REQUEST_ID = "INSERT INTO REQUEST (DOMAIN_ID) VALUES (?)";

    private static String GET_DOMAIN_RANKINGS_PER_MONTH =
            "SELECT KEYPHRASE.ID, PHRASE, URL, RANKING, REQUEST.TIMESTAMP, REQUEST.ID FROM KEYPHRASE, " +
            "DOMAIN_RANKING, REQUEST WHERE DOMAIN_RANKING.KEYPHRASE_ID=KEYPHRASE.ID AND " +
            "DOMAIN_RANKING.REQUEST_ID=REQUEST.ID AND MONTH(REQUEST.TIMESTAMP)=?" +
            " AND DOMAIN_RANKING.DOMAIN_ID=? ORDER BY KEYPHRASE.ID, REQUEST_ID ASC";

    // Potential add an AND URL IS NOT NULL here
    private static String GET_ALL_DOMAIN_RANKINGS=
            "SELECT KEYPHRASE.ID, PHRASE, URL, RANKING, REQUEST.TIMESTAMP, REQUEST.ID FROM KEYPHRASE, " +
            "DOMAIN_RANKING, REQUEST WHERE DOMAIN_RANKING.KEYPHRASE_ID=KEYPHRASE.ID AND " +
            "DOMAIN_RANKING.REQUEST_ID=REQUEST.ID " +
            "AND DOMAIN_RANKING.DOMAIN_ID=? " +
            "ORDER BY KEYPHRASE.PHRASE, REQUEST_ID ASC";

    private static String GET_ALL_DOMAIN_RANKINGS_BY_MONTH=
            "SELECT KEYPHRASE.ID, PHRASE, URL, RANKING, MONTH(REQUEST.TIMESTAMP), REQUEST.TIMESTAMP, REQUEST.ID FROM KEYPHRASE, " +
            "DOMAIN_RANKING, REQUEST WHERE DOMAIN_RANKING.KEYPHRASE_ID=KEYPHRASE.ID AND " +
            "DOMAIN_RANKING.REQUEST_ID=REQUEST.ID " +
            "AND DOMAIN_RANKING.DOMAIN_ID=? " +
            "ORDER BY KEYPHRASE.ID, REQUEST_ID ASC";

    private static String GET_RANKING_MONTHS_FOR_DOMAIN=
            "SELECT DISTINCT(MONTH(TIMESTAMP)), YEAR(TIMESTAMP) FROM DOMAIN_RANKING " +
            "WHERE DOMAIN_ID=?";

    private static String GET_ALL_REQUESTS_FOR_DOMAIN =
            "SELECT ID, TIMESTAMP FROM REQUEST WHERE ID in" +
            " (SELECT REQUEST_ID FROM DOMAIN_RANKING WHERE DOMAIN_ID=?)";

    private static String GET_RANKINGS_BY_KEYPHRASE_AND_REQUEST =
            "SELECT RANKING, URL FROM RANKING WHERE KEYPHRASE_ID=? and REQUEST_ID=? ORDER BY RANKING ASC";

    private static String GET_DOMAIN_RANKINGS_BY_REQUEST =
            "SELECT TIMESTAMP, KEYPHRASE_ID, PHRASE, RANKING, URL FROM DOMAIN_RANKING, KEYPHRASE WHERE REQUEST_ID=? " +
            "AND DOMAIN_RANKING.KEYPHRASE_ID = KEYPHRASE.ID ORDER BY PHRASE ASC";

    private static String GET_REQUEST_BY_ID =
            "SELECT TIMESTAMP FROM REQUEST WHERE ID=?";

    private static String GET_BEST_RANKINGS_BY_KEYPHRASE_AND_MONTH =
            "SELECT DISTINCT(URL), MIN(RANKING) FROM RANKING WHERE KEYPHRASE_ID=? AND " +
            "MONTH(TIMESTAMP)=? AND YEAR(TIMESTAMP)=? GROUP BY URL ORDER BY MIN(RANKING) ASC";


    
    public static long getRequestId(int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();

        long requestId = 0;
        try
        {

            ps = db.getPreparedStatement(GET_REQUEST_ID);
            ps.setInt(1, domainId);
            ps.execute();
            rs = ps.getGeneratedKeys();
            while (rs.next())
            {
                requestId=rs.getLong(1);
            }

            if (requestId==0)
            {
                log.error("Error getting request id.");
            }



            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }

        catch (Exception e)
        {
            e.printStackTrace();
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

            return requestId;
        }

    }


    /**
     * Returns an ArrayList of Request objects for all ranking requests for this domain
     * @param domainId
     * @return
     */
    public static ArrayList getAllRequestsForDomain(int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        ArrayList requests = new ArrayList();

        try
        {

            ps = db.getPreparedStatement(GET_ALL_REQUESTS_FOR_DOMAIN);
            ps.setInt(1, domainId);
            rs = ps.executeQuery();

            while (rs.next())
            {
                Request r = new Request(rs.getLong(1), rs.getTimestamp(2));
                requests.add(r);
            }


            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }

        catch (Exception e)
        {
            e.printStackTrace();
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

            return requests;
        }

    }

     public static ArrayList getRankingsByKeywordAndRequest(int keywordId, int requestId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        ArrayList rankings = new ArrayList();

        try
        {

            ps = db.getPreparedStatement(GET_RANKINGS_BY_KEYPHRASE_AND_REQUEST);
            ps.setInt(1, keywordId);
            ps.setInt(2, requestId);

            rs = ps.executeQuery();

            while (rs.next())
            {
                Ranking r = new Ranking(rs.getInt(1), rs.getString(2));
                rankings.add(r);
            }


            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }

        catch (Exception e)
        {
            e.printStackTrace();
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

            return rankings;
        }

    }

    public static ArrayList getBestRankingsByKeywordAndMonth(int keywordId, MonthYear m)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        ArrayList rankings = new ArrayList();

        try
        {

            ps = db.getPreparedStatement(GET_BEST_RANKINGS_BY_KEYPHRASE_AND_MONTH);
            ps.setInt(1, keywordId);
            ps.setInt(2, m.getId());
            ps.setInt(3, m.getYear());

            rs = ps.executeQuery();

            while (rs.next())
            {
                Ranking r = new Ranking(rs.getInt(2), rs.getString(1));
                rankings.add(r);
            }


            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }

        catch (Exception e)
        {
            e.printStackTrace();
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

            return rankings;
        }
           
    }

    /**
     * Gets all rankings for a certain request id
     * Retusns an ArrayList of Ranking objects
     * @param requestId
     * @return
     */
    public static ArrayList getRankingsByRequest(long requestId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        ArrayList rankings = new ArrayList();

        try
        {

            ps = db.getPreparedStatement(GET_DOMAIN_RANKINGS_BY_REQUEST);
           
            ps.setLong(1, requestId);

            rs = ps.executeQuery();

            while (rs.next())
            {
                Ranking r = new Ranking();
                r.setTimestamp(rs.getTimestamp(1));
                r.setKeyword(new Keyphrase(rs.getLong(2), rs.getString(3)));
                r.setRanking(rs.getInt(4));
                r.setUrl(rs.getString(5));
                rankings.add(r);
            }


            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }

        catch (Exception e)
        {
            e.printStackTrace();
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

            return rankings;
        }

    }

    /**
     * Returns an ArrayList of Months for which rankings have been achieved for this domain
     * @param domainId
     * @return
     */
    public static ArrayList getDomainRankingMonths(int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        ArrayList months = new ArrayList();

        try
        {

            ps = db.getPreparedStatement(GET_RANKING_MONTHS_FOR_DOMAIN);
            ps.setInt(1, domainId);
            rs = ps.executeQuery();

            while (rs.next())
            {
                // The second string is the year, the first string is the month
                MonthYear m = Data.getMonthYear(new Integer(rs.getString(1) + rs.getString(2)));
                log.debug("Just got monthYear " + m.getName() );
                months.add(m);
            }

         
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }

        catch (Exception e)
        {
            e.printStackTrace();
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

            return months;
        }

    }

    public static ArrayList getDomainRankingsPerMonth(int domainId, int month)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        ArrayList rankings = new ArrayList();
        long requestId = 0;
        try
        {

            ps = db.getPreparedStatement(GET_DOMAIN_RANKINGS_PER_MONTH);
            ps.setInt(1, month);
            ps.setInt(2, domainId);
            rs = ps.executeQuery();
            
            while (rs.next())
            {
                Ranking r = new Ranking();
                Keyphrase k = new Keyphrase(rs.getLong(1), rs.getString(2));
                r.setKeyword(k);
                r.setUrl(rs.getString(3));
                r.setRanking(rs.getInt(4));
                r.setTimestamp(rs.getTimestamp(5));
                r.setRequest(new Request(rs.getLong(6), rs.getTimestamp(5)));

                rankings.add(r);
            }

            if (requestId==0)
            {
                log.error("Error getting request id.");
            }



            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }

        catch (Exception e)
        {
            e.printStackTrace();
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

            return rankings;
        }

    }

     public static ArrayList getAllDomainRankings(int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        ArrayList rankings = new ArrayList();
       
        try
        {

            ps = db.getPreparedStatement(GET_ALL_DOMAIN_RANKINGS);

            ps.setInt(1, domainId);
            rs = ps.executeQuery();

            while (rs.next())
            {
                Ranking r = new Ranking();
                Keyphrase k = new Keyphrase(rs.getLong(1), rs.getString(2));
                r.setKeyword(k);
                r.setUrl(rs.getString(3));
                r.setRanking(rs.getInt(4));
                r.setTimestamp(rs.getTimestamp(5));
                r.setRequest(new Request(rs.getLong(6), rs.getTimestamp(5)));
                
                rankings.add(r);
            }

            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }

        catch (Exception e)
        {
            e.printStackTrace();
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

            return rankings;
        }

    }


      public static ArrayList getAllDomainRankingsByMonth(int domainId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        ArrayList rankings = new ArrayList();

        try
        {

            ps = db.getPreparedStatement(GET_ALL_DOMAIN_RANKINGS_BY_MONTH);

            ps.setInt(1, domainId);
            rs = ps.executeQuery();

            while (rs.next())
            {
                Ranking r = new Ranking();
                Keyphrase k = new Keyphrase(rs.getLong(1), rs.getString(2));
                r.setKeyword(k);
                r.setUrl(rs.getString(3));
                r.setRanking(rs.getInt(4));
                r.setMonth(Data.getMonthYear(rs.getInt(5)));
                r.setRequest(new Request(rs.getLong(7), rs.getTimestamp(6)));
                rankings.add(r);
            }

            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
        }

        catch (Exception e)
        {
            e.printStackTrace();
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

            return rankings;
        }

    }


    public static int insertRankingResult(String url, int ranking,
            long rankingHistoryId, int keyphraseId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();

        int userOrderId = 0;
        try
        {
            ps = db.getPreparedStatement(INSERT_RANKING_RESULT);
            ps.setLong(1, rankingHistoryId);
            ps.setInt(2, keyphraseId);
            ps.setString(3, url);
            ps.setInt(4, ranking);
            ps.execute();

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

            return userOrderId;
        }

    }

    public static long insertRankingResults(ArrayList rankings,
            long rankingHistoryId, long keyphraseId, long requestId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();

        long rankingResultId = 0;
        try
        {
            Iterator i = rankings.iterator();
            while (i.hasNext())
            {
                Ranking r = (Ranking)i.next();
                ps = db.getPreparedStatement(INSERT_RANKING_RESULT);
                ps.setLong(1, rankingHistoryId);
                ps.setLong(2, keyphraseId);
                ps.setString(3, r.getUrl());
                ps.setInt(4, r.getRanking());
                ps.setLong(5, requestId);
                ps.execute();
                rs = ps.getGeneratedKeys();
                while (rs.next())
                {
                    rankingResultId=rs.getLong(1);
                }

                if (rankingResultId==0)
                {
                    log.error("Error entering ranking result. Ranking history id-"+rankingHistoryId);
                }
            }


            if (rankings.size()==0)
            {
                log.error("No rankings passed in to insertRankingResults");
            }
            else
            {
                ps.close();
                ps = null;
                db.destroy();
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
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

            return rankingHistoryId;
        }

    }

    public static int insertRankingHistory(SearchEngineSetting ses, long requestId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();

        int rankingHistoryId = 0;
        try
        {
            ps = db.getPreparedStatement(INSERT_RANKING_HISTORY);
            ps.setString(1, ses.getSearchEngine());
            ps.setInt(2, ses.getNumResults());
            ps.setString(3, ses.getLanguage());
            ps.setString(4, ses.getBrowserClient());
            ps.setLong(5, requestId);
            ps.execute();

            rs = ps.getGeneratedKeys();
            while (rs.next())
            {
                rankingHistoryId = rs.getInt(1);
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

            return rankingHistoryId;
        }


    }

    public static long insertDomainRanking(int domainId, long keyphraseId, Ranking r, long rankingHistoryId, long requestId )
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();

        long domainRankingId = 0;
        try
        {
            ps = db.getPreparedStatement(INSERT_DOMAIN_RANKING);
            ps.setInt(1, domainId);
            ps.setLong(2, keyphraseId);
            ps.setInt(3, r.getRanking());
            ps.setString(4, r.getUrl());
            ps.setLong(5, rankingHistoryId);
            ps.setLong(6, requestId);
            ps.execute();

            rs = ps.getGeneratedKeys();
            while (rs.next())
            {
                domainRankingId = rs.getLong(1);
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
            e.printStackTrace();
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




    public static ArrayList getInfo()
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

            ps = db.getPreparedStatement("SELECT NAME FROM DOMAIN");
            // Here set any variables in prepared statement
            // e.g. //ps.setInt(1, orderStatusId);
            //log.info("About to execute query");
            rs = ps.executeQuery();
            //log.info("Executed query");
            while (rs.next())
            {
                info.add(rs.getString(1));


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


    public static Request getRequestById(long requestId)
    {
        ResultSet rs = null;
        PreparedStatement ps = null;
        // Get driver and connection by creating instance of DBConnection
        DBConnection db = new DBConnection();
        Request r = new Request();
        try
        {
            //log.info("About to build statement");

            ps = db.getPreparedStatement(GET_REQUEST_BY_ID);
            ps.setLong(1, requestId);

            rs = ps.executeQuery();
            //log.info("Executed query");
            while (rs.next())
            {
                r.setDate(rs.getTimestamp(1));
                r.setId(requestId);


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

            return r;
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