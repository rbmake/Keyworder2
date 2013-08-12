/*
 * 
 */

package forms;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author robb
 */
public class AddKeyphrasesForm extends ActionForm {
    
    private String keyphrases;
    
    
    public AddKeyphrasesForm() {
        //
    }

    public String getKeyphrases()
    {
        return keyphrases;
    }

    public void setKeyphrases(String keyphrases)
    {
        this.keyphrases = keyphrases;
    }

   
    
}
