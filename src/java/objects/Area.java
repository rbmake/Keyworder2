/*
 * Area.java
 */

package objects;
import java.io.Serializable;

/**
 *
 * @author robb
 */
public class Area implements Serializable
{
    
    private int id;
    private String name;
    
    /** Creates a new instance of Area */
    public Area()
    {
        
    }
    
    public Area(int id, String name)
    {
        this.setId(id);
        this.setName(name);
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
            name = Data.getArea(id).getName();
        }
        
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    
    
}
