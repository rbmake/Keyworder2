/*
 * Country.java
 *
 * Created on 04 November 2006, 14:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package objects;
import java.io.Serializable;

/**
 *
 * @author robb
 */
public class Country implements Serializable
{
    
    private int id;
    private String name;
    private String code;
    private Area area;
    /** Creates a new instance of Country */
    public Country()
    {
        
    }
    
    public Country(int id, String name, String code, int areaId)
    {
        this.setId(id);
        this.setName(name);
        this.setCode(code);
        this.setArea(Data.getArea(areaId));
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        if (name==null)
        {
            name = Data.getCountryName(id);
        }
        
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    
}
