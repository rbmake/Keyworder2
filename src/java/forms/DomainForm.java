/*
 * 
 */

package forms;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author robb
 */
public class DomainForm extends ActionForm {
    
    private String name;
    
    
    public DomainForm() {
        //
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
