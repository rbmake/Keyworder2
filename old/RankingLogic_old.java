/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logic;

import dao.RankingDAO;


import keyworder.Keyworder;
import rb.utils.tools.*;
import constants.Constants;
import objects.*;
import request.HttpPage;

import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author robbrown
 */
public class RankingLogic
{
    private static Log log = LogFactory.getLog(RankingLogic.class);

    public static void getSearchResults
            (Domain domain, SearchEngineSetting ses)
    {

        int placing=0;
        boolean bFound = true;
        HashMap placings = new HashMap();

        

        // Check if need to record timings
        String timings = Props.getProperty("timings");
        boolean bTimings = new Boolean(timings);

        //Keyworder.setProperty(FILE_ROOT_KEY, path);



        //ArrayList keywords = getKeywords(keywordsFile);

        // Get keywords for the domain
        // This returns an ArrayList of Keyword objects
        ArrayList keywords = KeywordLogic.getKeyphrases(domain);

        Iterator i = keywords.iterator();
        while ((i.hasNext()) && (bFound==true))
        {
            Keyword keyword = (Keyword)i.next();

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

            if (bTimings) {log.info("Time before get page is " + DateTool.getDateAsString(DateTool.getCurrentDateGMT(), DateTool.DATE_FORMAT_TIME_ONLY));}

            // Get Google search page
            String page = HttpPage.getPage(googleSearchUrl);
            if (bTimings) {log.info("Time after get page is " + DateTool.getDateAsString(DateTool.getCurrentDateGMT(), DateTool.DATE_FORMAT_TIME_ONLY));}

            if (page==null)
            {
                log.info("Cannot reach Google page. Please check you have an Internet connection.");
                bFound=false;

            }
            else
            {
                if (bTimings) {log.info("Time before get ranking is " + DateTool.getDateAsString(DateTool.getCurrentDateGMT(), DateTool.DATE_FORMAT_TIME_ONLY));}

                // Get rankings for this keyphrase
                ArrayList rankings = RankingLogic.getRankings(page);

                // Get ranking history id. This is an id that relates to this ranking for a set of keywords for a domain
                long rankingHistoryId = RankingLogic.getNextRankingHistoryId(ses);

                // Store all ranking results for this keyphrase
                RankingLogic.storeRankingResults(rankings, rankingHistoryId, keyword);

                // Store the ranking for this keyphrase for this domain
                RankingLogic.storePlaceInRankings(rankings,domain, rankingHistoryId, keyword);

                //placing = RankingLogic.getPlaceInNormalRankings(page,domain);

                if (bTimings) {log.info("Time after get ranking is " + DateTool.getDateAsString(DateTool.getCurrentDateGMT(), DateTool.DATE_FORMAT_TIME_ONLY));};

                //placings.put(keyword, placing);

            }
        }

        /*
        if (!placings.isEmpty())
        {
            FileTool.writeExcelHtmlFileFromHashMap(outputDirectory + "/keywords.html", placings);

        }*/

    }

    public static long storeRankingResults(ArrayList rankings, long rankingHistoryId, Keyword keyword)
    {
        return RankingDAO.insertRankingResults(rankings, rankingHistoryId, keyword.getId());
    }

    public static long storePlaceInRankings(ArrayList rankings, Domain domain, long rankingHistoryId, Keyword keyword)
    {
        int rank = getRankWithinRankings(rankings, domain.getName());

        return RankingDAO.insertDomainRanking(domain.getId(), keyword.getId(), rank, rankingHistoryId);
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
        ArrayList al = StringTool.getIndexesOf(page, Keyworder.getProperty(Constants.GOOGLE_NORMAL_RANKINGS_START_TAG_KEY));
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
                s = StringTool.substringInclusiveStartAt(page, index, Keyworder.getProperty(Constants.GOOGLE_NORMAL_RANKINGS_END_TAG_KEY));
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
        ArrayList al = StringTool.getIndexesOf(page, Keyworder.getProperty(Constants.GOOGLE_NORMAL_RANKINGS_START_TAG_KEY));
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
                s = StringTool.substringInclusiveStartAt(page, index, Keyworder.getProperty(Constants.GOOGLE_NORMAL_RANKINGS_END_TAG_KEY));

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



    public static int getRankWithinRankings(ArrayList rankings, String domainURL)
    {
        Iterator i = rankings.iterator();
        int ranking=1;
        while (i.hasNext())
        {
            Ranking r = (Ranking)i.next();
            if (StringTool.contains(r.getUrl(), domainURL))
            {
                return ranking;
            }
            else
            {
                ranking++;
            }
        }
        return 0;
    }

    public static long getNextRankingHistoryId(SearchEngineSetting ses)
    {
        return RankingDAO.insertRankingHistory(ses);
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

}
