/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;
import rb.utils.tools.StringTool;

/**
 *
 * @author robbrown
 */
public class Keyphrase {

    private long id;
    private String name;
    private Domain domain;
    private boolean doSearch;

    public Keyphrase(long id, String name)
    {
        this.id=id;
        this.name=name;
    }

    public Keyphrase()
    {
        
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getSentenceName()
    {
        return StringTool.getSentenceName(name);
    }

    /**
     * @return the doSearch
     */
    public boolean isDoSearch() {
        return doSearch;
    }

    /**
     * @param doSearch the doSearch to set
     */
    public void setDoSearch(boolean doSearch) {
        this.doSearch = doSearch;
    }

    /**
     * @return the domain
     */
    public Domain getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(int domainId) {
        this.domain = new Domain(domainId);
    }

}
