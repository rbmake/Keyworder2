/*
 * Title.java
 *
 * Created on 30 October 2006, 21:59
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
public class Title implements Serializable {
    
    private int id;
    private String name;
    /** Creates a new instance of Title */
    public Title() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
