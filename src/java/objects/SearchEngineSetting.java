/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

/**
 *
 * @author robbrown
 */
public class SearchEngineSetting {

    private String searchEngine;
    private int numResults;
    private String language;
    private String browserClient;

    /**
     * @return the searchEngine
     */
    public String getSearchEngine() {
        return searchEngine;
    }

    /**
     * @param searchEngine the searchEngine to set
     */
    public void setSearchEngine(String searchEngine) {
        this.searchEngine = searchEngine;
    }

    /**
     * @return the numResults
     */
    public int getNumResults() {
        return numResults;
    }

    /**
     * @param numResults the numResults to set
     */
    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the browserClient
     */
    public String getBrowserClient() {
        return browserClient;
    }

    /**
     * @param browserClient the browserClient to set
     */
    public void setBrowserClient(String browserClient) {
        this.browserClient = browserClient;
    }


}
