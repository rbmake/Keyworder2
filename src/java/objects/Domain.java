/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

/**
 *
 * @author robbrown
 */
public class Domain {

    private int id;
    private String name;

    public Domain()
    {

    }

    public Domain(int domainId)
    {
        setId(domainId);
    }

    public Domain(int domainId, String domainName)
    {
        setId(domainId);
        setName(domainName);
    }


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
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

}
