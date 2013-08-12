/*
 * 
 */

package forms;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author robb
 */
public class MountainsForm extends ActionForm {
    
    private String[] mountains;
    
    
    public MountainsForm() {
        //
    }

    /**
     * @return the mountains
     */
    public String[] getMountains() {
        return mountains;
    }

    /**
     * @param mountains the mountains to set
     */
    public void setMountains(String[] mountains) {
        this.mountains = mountains;
    }

    

   
    
}
