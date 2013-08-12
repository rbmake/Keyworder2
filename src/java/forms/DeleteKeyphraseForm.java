/*
 * LoginForm.java
 *
 * Created on 25 October 2006, 21:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package forms;
import org.apache.struts.action.*;
import org.apache.struts.validator.ValidatorForm;
/**
 *
 * @author robb
 */
public class DeleteKeyphraseForm extends ValidatorForm {
    
    private String yesNo;
   
    private Integer id;
    
    public DeleteKeyphraseForm() {
    }

  
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the yesNo
     */
    public String getYesNo() {
        return yesNo;
    }

    /**
     * @param yesNo the yesNo to set
     */
    public void setYesNo(String yesNo) {
        this.yesNo = yesNo;
    }

    
    
}
