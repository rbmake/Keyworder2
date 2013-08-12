/*
 * RegisterForm.java
 *
 *
 */

package forms;
import objects.Data;
import objects.Title;
import org.apache.struts.action.*;
import org.apache.struts.validator.ValidatorForm;
/**
 *
 * @author robb
 */
public class RegisterForm extends ValidatorForm {
    
    private Title title;
    private int titleId;
    private Long id;
    private String firstName;
    private String surname;
    private String registerEmail;
    private String registerPassword;
    private String confirmPassword;
    private String telephoneNumber;
    private boolean receiveEmailNewsletter;
    
    /** Creates a new instance of LoginForm */
    public RegisterForm() {
    }

    

   
    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
        setTitle(titleId);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
   
    public void setTitle(int id)
    {
        setTitle(Data.getTitle(id));
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRegisterEmail()
    {
        return registerEmail;
    }

    public void setRegisterEmail(String registerEmail)
    {
        this.registerEmail = registerEmail;
    }

    public String getRegisterPassword()
    {
        return registerPassword;
    }

    public void setRegisterPassword(String registerPassword)
    {
        this.registerPassword = registerPassword;
    }

    public boolean getReceiveEmailNewsletter()
    {
        return receiveEmailNewsletter;
    }

    public void setReceiveEmailNewsletter(boolean receiveEmailNewsletter)
    {
        this.receiveEmailNewsletter = receiveEmailNewsletter;
    }
    
    
}
