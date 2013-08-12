/*
 * CardType.java
 *
 * Created on 05 November 2006, 08:16
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
public class Year implements Serializable
{
    
    private int id;
    private String name;
    
    
    /** Creates a new instance of CardType */
    public Year()
    {
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
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    
    public int getYearAsInt()
    {
        return new Integer(this.getName()).intValue();
    }

   
    
}
