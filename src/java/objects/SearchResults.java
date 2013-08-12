/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;
import logic.RankingLogic;

/**
 *
 * @author robbrown
 */
public class SearchResults extends Thread
{
    private Domain domain;
    private SearchEngineSetting ses;

    public SearchResults(Domain d, SearchEngineSetting s)
    {
        this.domain=d;
        this.ses=s;
        setDaemon(true);
        start();
    }

    public void run()
    {
        RankingLogic.getSearchResults(domain, ses);
    }

}
