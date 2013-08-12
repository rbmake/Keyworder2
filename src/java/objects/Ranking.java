/*
 * 
 */

package objects;
import java.util.Date;
import rb.utils.tools.DateTool;

/**
 *
 * @author robbrown
 */
public class Ranking implements Comparable{

    private String url;
    private Keyphrase keyword;
    private int ranking;
    private Date timestamp;
    private MonthYear month;
    private Week week;
    private Request request;

    public Ranking()
    {

    }

    public Ranking(int ranking, String url)
    {
        setRanking(ranking);
        setUrl(url);
    }

    public Ranking(Keyphrase keyphrase, int ranking, String url, MonthYear month)
    {
         setKeyword(keyphrase);
         setRanking(ranking);
         setUrl(url);
         setMonth(month);
    }

    public int compareTo(Object o)
    {
        Ranking r = (Ranking) o;

        // If input ranking is not 0 and input ranking is less than this ranking then return 1 i.e. true that better
        if (r.getRanking()!=0 && (r.getRanking()<getRanking() || getRanking()==0))
        {
            //return new Integer(getRanking()).compareTo(new Integer(r.getRanking()));
            return 1;
        }
        // Input ranking is greater than this one i.e. this one is better
        else if (r.getRanking()!=0 && r.getRanking()>getRanking())
        {
            //return new Integer(getRanking()).compareTo(new Integer(r.getRanking()));
            return -1;
        }
        // Input ranking is 0 and this one is not 0 i.e. this one is better
        else if (r.getRanking()==0 && getRanking()!=0)
        {
            // Return the comparison the other way
            //return new Integer(r.getRanking()).compareTo(new Integer(getRanking()));
            return -1;
        }
       

        // Same rankings so sort lexigographically
        else if (r.getRanking()==getRanking())
        {
            // This will return 0 if they are exactly the same keyword (shouldn't be)
            // or will return negative value if this is better (lexicographically less) than input ranking
            // or will return positive value if this is worse than input ranking
            return getKeyword().getName().compareToIgnoreCase(r.getKeyword().getName());

            //return r.getKeyword().getName().compareToIgnoreCase(getKeyword().getName());

        }
        // Should never get here
        else
        {
            return 0;
        }

    }


    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the ranking
     */
    public int getRanking() {
        return ranking;
    }

    /**
     * @param ranking the ranking to set
     */
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    /**
     * @return the keyword
     */
    public Keyphrase getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(Keyphrase keyword) {
        this.keyword = keyword;
    }

    /**
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        this.setMonth(new MonthYear(DateTool.getMonthFromDate(timestamp), DateTool.getYearFromDate(timestamp)));

    }

    /**
     * @return the month
     */
    public MonthYear getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(MonthYear month) {
        this.month = month;
    }

    /**
     * @return the week
     */
    public Week getWeek() {
        return week;
    }

    /**
     * @param week the week to set
     */
    public void setWeek(Week week) {
        this.week = week;
    }

    /**
     * @return the request
     */
    public Request getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(Request request) {
        this.request = request;
    }



}
