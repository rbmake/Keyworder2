/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author robbrown
 */
public class RankingHistory {

    private ArrayList rankings;
    private Keyphrase keyword;

    

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
     * @return the rankings
     */
    public ArrayList getRankings() {
        return rankings;
    }

    /**
     * @param rankings the rankings to set
     */
    public void setRankings(ArrayList rankings) {
        this.rankings = rankings;
    }

    public void setRankings(Vector vRankings)
    {
        Iterator j = vRankings.iterator();
        rankings=new ArrayList();
        while (j.hasNext())
        {
            Ranking r = (Ranking)j.next();
            if (r!=null)
            {
                rankings.add(r);
            }
        }
        
    }

}
