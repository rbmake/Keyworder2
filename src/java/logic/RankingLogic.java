/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import dao.RankingDAO;

import rb.utils.tools.*;
import constants.Constants;
import objects.*;
import request.HttpPage;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Collections;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author robbrown
 */
public class RankingLogic
{
    private static Log log = LogFactory.getLog(RankingLogic.class);


    /**
     * Gets the search results for a specific domain and search engine setting
     * 1. Gets a request id - this is use for all logging
     * 2. Gets all the keyphrases that have a do_search=true for this domain
     * 3. For each keyphrase do the Google search
     * 4. For all the results return do RankingLogic.getRankings()
     * 5. If log debug enabled write all the URLs returned to the log
     * 6. Get ranking history id. This is an id that relates to this ranking for a set of keywords for a domain
     * 7. Store all ranking results for this keyphrase in
     * @param domain
     * @param ses
     */
    public static void getSearchResults
            (Domain domain, SearchEngineSetting ses)
    {

        //int placing=0;
        boolean bFound = true;
        //HashMap placings = new HashMap();


        // Check if need to record timings
        String timings = Props.getProperty("timings");
        boolean bTimings = new Boolean(timings);

        // Check to see if output Google search string
        String showGoogleSearchStrings = Props.getProperty("showGoogleSearchStrings");
        boolean bShowGoogleSearchStrings  = new Boolean(showGoogleSearchStrings);


        // Check to see if should show page returned
        String showPage = Props.getProperty("showPage");
        boolean bShowPage  = new Boolean(showPage);
        
        // Get a request id
        long requestId = RankingDAO.getRequestId(domain.getId());

        
        // Get keywords for the domain
        // This returns an ArrayList of Keyword objects
        ArrayList keywords = KeyphraseLogic.getActiveKeyphrases(domain);

        Iterator i = keywords.iterator();
        while ((i.hasNext()) && (bFound==true))
        {
            Keyphrase keyword = (Keyphrase)i.next();

            // Build URL for search
            String keyphrase = keyword.getName();
            ArrayList words = StringTool.split(keyphrase, ' ');
            Iterator w = words.iterator();
            String keywordString = new String();
            while (w.hasNext())
            {
                keywordString = keywordString + (String)w.next() + "+";
            }

            // Put together full Google URL
            String googleSearchUrl = "http://www.google.co.uk/search?num=" + ses.getNumResults() + "&hl=en&client=" + ses.getBrowserClient() + "&q=" + keywordString;

            if (bShowGoogleSearchStrings) {log.info("Google search string is " + googleSearchUrl);}

            if (bTimings) {log.info("Time before get page is " + DateTool.getDateAsString(DateTool.getCurrentDateGMT(), DateTool.DATE_FORMAT_TIME_ONLY));}

            // Get Google search page
            String page = HttpPage.getPage(googleSearchUrl);

            if (bTimings) {log.info("Time after get page is " + DateTool.getDateAsString(DateTool.getCurrentDateGMT(), DateTool.DATE_FORMAT_TIME_ONLY));}

            if (page==null)
            {
                log.info("Cannot reach Google page. Please check you have an Internet connection.");
                bFound=false;

            }
            else if (bShowPage)
            {
                log.debug("Page is\n" + page);
            }

            String b = Props.getProperty("test.mode");
            if (b.equalsIgnoreCase("true"))
            {
                page="<h3 class=\"r\"><a href=\"http://www.french-ski-property.com\" class=l>Thollon les Memises Apartment with Lake <em>Geneva</em> Views and 50m from <b>...</b></a></h3>";
                bFound=true;
            }

            if (bFound==true)
            {
                if (bTimings) {log.info("Time before get ranking is " + DateTool.getDateAsString(DateTool.getCurrentDateGMT(), DateTool.DATE_FORMAT_TIME_ONLY));}
                //log.info("Page is " + page);

                // Get rankings for this keyphrase
                ArrayList rankings = RankingLogic.getRankings(page);

                /*
                 * If log debug enabled then show all the ranking returned
                 */
                if (log.isDebugEnabled())
                {
                    Iterator r = rankings.iterator();
                    while (r.hasNext())
                    {
                        Ranking ranking = (Ranking)r.next();
                        log.debug(ranking.getUrl());
                    }
                }

                // Get ranking history id. This is an id that relates to this ranking for a set of keywords for a domain
                long rankingHistoryId = RankingLogic.getNextRankingHistoryId(ses, requestId);

                // Store all ranking results for this keyphrase
                RankingLogic.storeRankingResults(rankings, rankingHistoryId, keyword, requestId);

                // Store the ranking for this keyphrase for this domain
                RankingLogic.storePlaceInRankings(rankings,domain, rankingHistoryId, keyword, requestId);

                //placing = RankingLogic.getPlaceInNormalRankings(page,domain);

                if (bTimings) {log.info("Time after get ranking is " + DateTool.getDateAsString(DateTool.getCurrentDateGMT(), DateTool.DATE_FORMAT_TIME_ONLY));};

                //placings.put(keyword, placing);

            }
        }



    }

    public static long storeRankingResults(ArrayList rankings, long rankingHistoryId, Keyphrase keyword, long requestId)
    {
        return RankingDAO.insertRankingResults(rankings, rankingHistoryId, keyword.getId(), requestId);
    }

     public static long storePlaceInRankings(ArrayList rankings, Domain domain, long rankingHistoryId, Keyphrase keyword, long requestId)
    {
        //log.info("About to call getRankWithinRankings for domain " + domain.getName());
        Ranking r = getDomainRankingWithinRankings(rankings, domain.getName());

        log.debug("About to store place in rankings. Ranking for request " + requestId + " for domain " +
                domain.getName() + " for this keyphrase" +
                keyword + " is " + r.getRanking());
        
        return RankingDAO.insertDomainRanking(domain.getId(), keyword.getId(), r, rankingHistoryId, requestId);
    }

    /**
     * Returns the Google placing within a page
     * @param page
     * @param url
     * @return Placing in normal rankings. Returns 0 if the url is not found
     */
    public static int getPlaceInNormalRankings(String page, String domainUrl)
    {
        //ArrayList rankings = new ArrayList();
        ArrayList al = StringTool.getIndexesOf(page, Props.getProperty(Constants.GOOGLE_NORMAL_RANKINGS_START_TAG_KEY));
        if (al!=null)
        {
            Iterator i = al.iterator();
            String s = new String();
            Integer index = new Integer(0);
            int domainPlacing = 1;
            int ranking = 1;
            while (i.hasNext())
            {
                index = (Integer)i.next();
                s = StringTool.substringInclusiveStartAt(page, index, Props.getProperty(Constants.GOOGLE_NORMAL_RANKINGS_END_TAG_KEY));
                if (StringTool.contains(s, domainUrl))
                {
                    return domainPlacing;
                }
                else
                {
                    domainPlacing++;
                }
                ranking++;
            }
        }
        return 0;
    }

    public static ArrayList getRankings(String page)
    {
        ArrayList rankings = new ArrayList();
        ArrayList al = StringTool.getIndexesOf(page, Props.getProperty(Constants.GOOGLE_NORMAL_RANKINGS_START_TAG_KEY));
        if (al!=null)
        {
            Iterator i = al.iterator();
            String s = new String();
            Integer index = new Integer(0);
            int ranking = 1;
            while (i.hasNext())
            {
                Ranking r = new Ranking();
                index = (Integer)i.next();
                s = StringTool.substringInclusiveStartAt(page, index, Props.getProperty(Constants.GOOGLE_NORMAL_RANKINGS_END_TAG_KEY));

                // Just want the site name
                s = StringTool.substring(s, "href=\"", '"');
                r.setRanking(ranking);
                r.setUrl(s);
                rankings.add(r);
                ranking++;
            }
        }
        return rankings;
    }



    public static Ranking getDomainRankingWithinRankings(ArrayList rankings, String domainURL)
    {
        Iterator i = rankings.iterator();
        int ranking=1;
        while (i.hasNext())
        {
            Ranking r = (Ranking)i.next();
            if (StringTool.contains(r.getUrl(), domainURL))
            {
                return r;
            }
            else
            {
                ranking++;
            }
        }

        // Could not find it therefore not ranked
        return new Ranking(0, domainURL);
    }

    public static long getNextRankingHistoryId(SearchEngineSetting ses, long requestId)
    {
        return RankingDAO.insertRankingHistory(ses, requestId);
    }

    public static String getGoogleBrowserString(String browser)
    {
        if (browser.equalsIgnoreCase(Constants.FIREFOX))
        {
            return "firefox-a";
        }
        else if (browser.equalsIgnoreCase(Constants.IE))
        {
            return null;
        }
        else if (browser.equalsIgnoreCase(Constants.CHROME))
        {
            return "chrome";
        }

        return null;

    }

    /**
     * Gets an ArrayList of Months for which rankings have been recorded for this domain
     * @param domainId
     * @return
     */
    public static ArrayList getDomainRankingMonths(int domainId)
    {
        return RankingDAO.getDomainRankingMonths(domainId);
    }

    /**
     * Returns an ArrayList of RankingHistory objects
     * 1. Gets all domain rankings ordered by keyphrase and then request id
     * 2. Sets the first Ranking object as the first from the list
     * 3. Gets the next ranking. If it is the same then it is added to an ArrayList of rankings for this keyphrase - rankingHistory
     * @param domainId
     * @return
     */
    public static ArrayList getAllDomainRankings(int domainId)
    {
        ArrayList rankings = RankingDAO.getAllDomainRankings(domainId);
        log.debug(rankings.size() + " rankings returned from getAllDomainRankings");

        ArrayList rankingHistories = new ArrayList();


        Iterator i = rankings.iterator();
        Ranking rankingToAdd = new Ranking();

        ArrayList rankingHistory = new ArrayList();

        // Get the first one
        if (i.hasNext())
        {
            rankingToAdd = (Ranking)i.next();
        }

        while (i.hasNext())
        {

            Ranking nextRanking = (Ranking)i.next();
            if (nextRanking.getKeyword().getId()==rankingToAdd.getKeyword().getId())
            {
                // Next one is the same so add the current one
                rankingHistory.add(rankingToAdd);

                if (log.isDebugEnabled())
                {
                    if (rankingToAdd.getKeyword().getName().equalsIgnoreCase("ski property geneva"))
                    {
                        log.debug("Next ranking has same keyword: Adding ski property geneva with ranking = " + rankingToAdd.getRanking() + " for request " + rankingToAdd.getRequest().getId() );
                    }
                }
                
            }

            else
            {
                // Different keyword so add rankingToAdd to the rankingHistory
                // Add this rankingHistory to the list of rankingHistories that are returned
                // and thisRanking becomes the new keyword
                rankingHistory.add(rankingToAdd);

                // Create a RankingHistory object with the ArrayList of rankings and keyword
                RankingHistory rh = new RankingHistory();
                rh.setRankings(rankingHistory);
                rh.setKeyword(rankingToAdd.getKeyword());

                if (log.isDebugEnabled())
                {
                    if (rankingToAdd.getKeyword().getName().equalsIgnoreCase("ski property geneva"))
                    {
                        log.debug("Adding ski property geneva with ranking = " + rankingToAdd.getRanking() + " for request " + rankingToAdd.getRequest().getId() );
                    }
                }

                // Add the RankingHistory to the whole ArrayList of RankingHistories
                rankingHistories.add(rh);

                // Reset the rankingHistory ArrayList
                rankingHistory = new ArrayList();
                
            }

            // Now set the next one to be the current
            rankingToAdd = nextRanking;

        }

        // TO DO
        // Now are at end and have a rankingToAdd
        // If this rankingToAdd 

        return rankingHistories;
    }


    /**
     * Returns the best ranking for each keyword for the months passed in for this domainId
     * Gets al the domain rankings ordered by keyphrase then request id as an ArrayList. THIS ORDERING IS IMPORTANT.
     * Gets the first domain ranking from the ArrayList
     * Goes through the remainder of the rankings.
     * 1) If the next keyword is the same as the current one then
     *  a) If the month is the same it works out which is better
     *  b) If the month is different then adds the current ranking as long as it is within the months passed in
     * 2) If the next keyword is different then add this ranking to those returned if within the months passed in
     *  Also if different then go through the list of passed in months and set the default ranking (-1) if a ranking does
     * not exist for a month.
     *
     * @param domainId
     * @param months
     * @return
     */
    public static ArrayList getBestRankingsByMonth(int domainId, ArrayList months)
    {

        // First get all the rankings for this domain
        ArrayList rankings = RankingDAO.getAllDomainRankings(domainId);

        if (log.isDebugEnabled())
        {
            Iterator i = rankings.iterator();
            while (i.hasNext())
            {

                Ranking r = (Ranking)i.next();
                //log.debug("Ranking is " + r.getRanking() + " for keyphrase " + r.getKeyword().getName() + " for month " + r.getMonth().getId());

            }
        }


        
        ArrayList rankingHistories = new ArrayList();

        // Create iterator to run through the rankings
        Iterator i = rankings.iterator();
        // The rankingToAdd is the current ranking that will be added. There is one ranking per keyword per month
        Ranking rankingToAdd = new Ranking();

        //ArrayList rankingHistory = new ArrayList();
        Vector rankingHistory = new Vector();
        rankingHistory.setSize(months.size());
        
        // Get the first one
        if (i.hasNext())
        {
            rankingToAdd = (Ranking)i.next();
        }

        while (i.hasNext())
        {

            Ranking nextRanking = (Ranking)i.next();
            
            // Next ranking keyword is same as current
            if (nextRanking.getKeyword().getId()==rankingToAdd.getKeyword().getId())
            {
                // Months are same
                if (nextRanking.getMonth().getId()==rankingToAdd.getMonth().getId())
                {
                    // So if nextRanking is better (less) and doesn't equal 0
                    // Or if nextRanking is better (more) and
                    if ((nextRanking.getRanking()<rankingToAdd.getRanking() && nextRanking.getRanking()!=0) ||
                            (nextRanking.getRanking()>0 && rankingToAdd.getRanking()==0))
                    {
                        // Do not add this one since next ranking is better
                        // Cannot add next ranking yet since the one after that may be better
                        // Now set the next one to be the current
                        rankingToAdd = nextRanking;
                    }
                    else
                    {
                        // RankingToAdd is better therefore want to discard nextRanking
                        // DO NOT SET RANKINGTOADD TO NEXTRANKING
                    }
                }
                else
                {
                    // Next ranking month is different - add current one
                    // But only if it is within the Months passed in

                    Iterator m = months.iterator();
                    
                    while (m.hasNext())
                    {
                        MonthYear month = (MonthYear)m.next();
                        if (month.getId()==rankingToAdd.getMonth().getId())
                        {
                            //log.debug("Adding " + rankingToAdd.getKeyword().getName() + " with ranking " + rankingToAdd.getRanking() + " for month " + rankingToAdd.getMonth().getId());
                            rankingHistory.add(rankingToAdd);
                            
                            break;
                        }
                        
                    }

                   

                    // Now set the next one to be the current
                    rankingToAdd = nextRanking;
                }
                
                

               
                
            }

            // Different keyword in nextRanking so add rankingToAdd as the final ranking in this history
            else
            {
                
                
                Iterator m = months.iterator();
                
                

                int iCount=0;
                while (m.hasNext())
                {
                    MonthYear month = (MonthYear)m.next();

                    if (month.getId()==rankingToAdd.getMonth().getId())
                    {
                        log.debug("Adding " + rankingToAdd.getKeyword().getName() + " with ranking " + rankingToAdd.getRanking() + " for month " + rankingToAdd.getMonth().getId());
                        // THE PROBLEM HERE IS THAT THIS COULD BE THE 7th month in the list and we are adding this
                        // to the start of the rankingHistory ArrayList
                        rankingHistory.add(rankingToAdd);
                        /* if (rankingToAdd.getKeyword().getName().equalsIgnoreCase("Ski property andermatt"))
                        {
                            log.debug("Adding rankingToAdd to Vector at position " + iCount);
                         }*/
                        //rankingHistory.add(iCount, rankingToAdd);

                        break;
                    }
                    iCount++;
                }
            

                // Now need to go through the ranking history for this keyphrase
                // If there is no entry for a certain month then need to enter the default in the correct place
                // in the Vector
                m = months.iterator();
                iCount=0;
                while (m.hasNext())
                {
                    boolean bFoundMonth=false;
                    log.debug("Ranking history is of size " + rankingHistory.size());
                    Iterator rh = rankingHistory.iterator();
                    MonthYear thisMonth = (MonthYear)m.next();
                    // Have a month now go through all the rankings to see if there is one that matches

                    while (rh.hasNext() && bFoundMonth==false)
                    {
                        Ranking r = (Ranking)rh.next();
                        //log.debug("Just got ranking " + r.getKeyword() + " for month " + r.getMonth().getName());
                        if (r!=null) // Must do this since we have set the size of the Vector and may have a null
                        {
                            MonthYear rankingMonth = (MonthYear)r.getMonth();
                            //log.debug("keyword is " + r.getKeyword().getName());
                            //log.debug("ranking month is " + rankingMonth.getId());
                            //log.debug("this month is " + thisMonth.getId());
                            if (rankingMonth.getId()==thisMonth.getId())
                            {
                                bFoundMonth=true;
                                //log.debug("Months match");

                            }
                        }
                    }

                    if (bFoundMonth==false)
                    {
                        // Add a default entry
                        Ranking defaultRanking = new Ranking(rankingToAdd.getKeyword(),Constants.DEFAULT_RANKING, Constants.BLANK_URL, thisMonth);
                        log.debug("Adding " + defaultRanking.getKeyword().getName() + " with default ranking " + defaultRanking.getRanking() + " for month" + thisMonth.getName());
                        //rankingHistory.add(defaultRanking);
                        rankingHistory.add(iCount,defaultRanking);
                        //log.debug("Adding defaultRanking to Vector at position " + iCount);
                    }

                    iCount++;
                }

                //Collections.copy(rankingHistory, vRankingHistory);
                /*Iterator j = vRankingHistory.iterator();
                while (j.hasNext())
                {
                    Ranking r = (Ranking)j.next();
                    rankingHistory.add(r);
                }*/


                // Create a RankingHistory object with the ArrayList of rankings and keyword
                RankingHistory rh = new RankingHistory();
                log.debug("About to set ranking history for " + rankingToAdd.getKeyword().getName());
                rh.setRankings(rankingHistory);
                rh.setKeyword(rankingToAdd.getKeyword());


                if (log.isDebugEnabled())
                {
                    if (rankingToAdd.getKeyword().getName().equalsIgnoreCase("Ski property andermatt"))
                    {
                        log.debug("Adding ski property andermatt with ranking = " + rankingToAdd.getRanking() + " for request " + rankingToAdd.getRequest().getId() );
                    }
                }

                // Add the RankingHistory to the whole ArrayList of RankingHistories
                rankingHistories.add(rh);

                // Reset the rankingHistory ArrayList
                //rankingHistory = new ArrayList();
                rankingHistory = new Vector();

                // Now set the next one to be the current
                rankingToAdd = nextRanking;
            }
                
            

            

        }

        
        // Now are at end and have a rankingToAdd
        // If this rankingToAdd
        rankingHistory.add(rankingToAdd);
        RankingHistory rh = new RankingHistory();
        rh.setRankings(rankingHistory);
        rh.setKeyword(rankingToAdd.getKeyword());
        rankingHistories.add(rh);

        return rankingHistories;
    }

    

    /**
     * This gets all the RankingHistories and then gets the ranking just for the month passed in
     * and adds it to a new ArrayList
     * It then sorts this ArrayList of rankings. This can be done since the Ranking object implements the Comparable
     * interface and the thus compulsory compareTo method.
     * Then for each ranking in this ArrayList of rankings it then gets the appropriate RankingHistory
     * @param domainId
     * @param monthId
     * @return
     */
    public static ArrayList getAllDomainRankingsSortedByMonth(int domainId, ArrayList months, int monthId)
    {
        ArrayList rankingHistories = getBestRankingsByMonth(domainId, months);
        ArrayList rankingsForMonth = new ArrayList();
        ArrayList newRankingHistories = new ArrayList();
        // Now go through rankingHistories and sort by ranking just for the month passed in
        Iterator i = rankingHistories.iterator();
        while (i.hasNext())
        {
            RankingHistory nextRH = (RankingHistory)i.next();
            ArrayList nextRankings = nextRH.getRankings();

            // Get the ranking for the month passed in
            Ranking nextRanking = getRankingForMonthFromRankings(nextRankings, monthId);
            rankingsForMonth.add(nextRanking);
        }

        Collections.sort(rankingsForMonth);

        // Now go through rankingsForMonth. For each one get the RankingHistory from rankingHistories
        ListIterator j = rankingsForMonth.listIterator();
        while (j.hasNext())
        {
            Ranking r = (Ranking)j.next();
            //log.debug("Got ranking with keyword = " + r.getKeyword().getName() + " and ranking = " + r.getRanking());
            Iterator k = rankingHistories.iterator();
            while (k.hasNext())
            {
                RankingHistory rh = (RankingHistory)k.next();
                if (r.getKeyword().getName().equalsIgnoreCase(rh.getKeyword().getName()))
                {
                    newRankingHistories.add(rh);
                }
            }

        }

        
        return newRankingHistories;

    }

    private static Ranking getRankingForMonthFromRankings(ArrayList rankings, int monthId)
    {
        Iterator j = rankings.iterator();
        Ranking r = new Ranking();
        while (j.hasNext())
        {
            r = (Ranking)j.next();
            if (r.getMonth().getId()==monthId)
            {
                break;
            }
        }
        return r;
    }

    public static ArrayList getRankingsByKeywordAndRequest(int keywordId, int requestId)
    {
        return RankingDAO.getRankingsByKeywordAndRequest(keywordId, requestId);
    }

    /*
    public static ArrayList getRankingsByKeywordAndMonthYear(int keywordId, String strMonthYear)
    {
        Month m = new Month(strMonthYear);
        ArrayList al = new ArrayList();
        // Get the best rankings for each URL for these keyword for this month & year
        ArrayList rankings = RankingDAO.getBestRankingsByKeywordAndMonth(keywordId, m);
        log.debug("Got " + rankings.size() + " rankings back from getBestRankingsByKeywordAndMonth with keyword id "
                + keywordId + " and month " + m.getId() + m.getYear());

        Iterator i = rankings.iterator();
        int iRanking=1;
        while (i.hasNext() && iRanking<=30)
        {
            Ranking r = new Ranking();
            r = (Ranking)i.next();
            r.setRanking(iRanking);
            al.add(r);
            iRanking++;
        }

        return al;
    }*/

    public static ArrayList getRankingsByRequest(long requestId)
    {
        return RankingDAO.getRankingsByRequest(requestId);
    }

    public static Request getRequestById(long requestId)
    {
        return RankingDAO.getRequestById(requestId);
    }

}
