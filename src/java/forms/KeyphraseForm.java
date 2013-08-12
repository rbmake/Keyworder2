/*
 * 
 */

package forms;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author robb
 */
public class KeyphraseForm extends ActionForm {
    
    private String[] keyphrase;
    
    
    public KeyphraseForm() {
        //
    }

    public String[] getKeyphrase()
    {
        return keyphrase;
    }

    public void setKeyphrase(String[] keyphrase)
    {
        this.keyphrase = keyphrase;
    }

   
    
}
