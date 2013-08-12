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
public class LoginForm extends ValidatorForm {
    
    private String email;
    private String password;
    
    /** Creates a new instance of LoginForm */
    public LoginForm() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
