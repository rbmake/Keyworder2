/*
 * User.java
 *
 */

package objects;
import rb.utils.tools.*;
import constants.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author robb
 */
public class User {
    
    private int id;
    private Title title;
    private String firstName;
    private String surname;
    private String email;
    private int numLogonFailures;
    private boolean isAccountLocked;
    private String enteredPassword;
    private String password;
    private String telephoneNumber;
    private boolean isLoggedOn;
    private boolean isSignedIn;
    private int uniqueCode;
    private Privilege privilege;
    private boolean receiveEmailNewsletter;
    private TrueFalse tfReceiveEmailNewsletter;
    
    private static Log log = LogFactory.getLog(User.class);
    
    /** Creates a new instance of User */
    public User() {
        this.isLoggedOn=false;
        this.isSignedIn=false;
    }
    
    public User(String surname)
    {
        this.isLoggedOn=false;
        this.isSignedIn=false;
        this.surname=surname;
    }
   
    
    public String getFirstName() {
        
        return firstName;
    }

    public String getFirstNameProper()
    {
        if (firstName!=null)
        {
            return StringTool.getProperName(firstName);
            
        }
        else
        {
            return null;
        }
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }
    
    public String getSurnameProper()
    {
        if (surname!=null)
        {
            return StringTool.getProperName(surname);
        }
        else
        {
            return null;
        }
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCombinedName()
    {
        return firstName + " " + surname;
    }
    
    public String getCombinedNameProper()
    {
        return StringTool.getProperName(getCombinedName());
        
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumLogonFailures() {
        return numLogonFailures;
    }

    public void setNumLogonFailures(int numLogonFailures) {
        this.numLogonFailures = numLogonFailures;
    }

    public boolean isIsAccountLocked() {
        return isAccountLocked;
    }

    public void setIsAccountLocked(boolean isAccountLocked) {
        this.isAccountLocked = isAccountLocked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean IsLoggedOn() {
        return isLoggedOn;
    }

    public void setIsLoggedOn(boolean isLoggedOn) {
        this.isLoggedOn = isLoggedOn;
    }

    public int getId()
    {
        return id;
    }
    
   
    
    public void setId(int id) {
        this.id = id;
    }
    
    

    public int getCookieCode() {
        return uniqueCode;
    }
    
    public String getCookieCodeAsString()
    {
        return new Integer(uniqueCode).toString();
    }

    public void setCookieCode(int uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
    
    public void setNewCookieCode()
    {
        setCookieCode(getNewCookieCode());        
    }
    
    public int getNewCookieCode()
    {
        CodeTool ct = new CodeTool();
        int i = 0;
        i = ct.getUniqueNumber();
        return i;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEnteredPassword() {
        return enteredPassword;
    }

    public void setEnteredPassword(String enteredPassword) {
        this.enteredPassword = enteredPassword;
    }

    public boolean isSignedIn() {
        return isSignedIn;
    }

    public void setIsSignedIn(boolean isSignedIn) {
        this.isSignedIn = isSignedIn;
    }

    public Title getTitle()
    {
        return title;
    }

    public void setTitle(Title title)
    {
        this.title = title;
    }
    
    public void setTitle(int t)
    {
        title = Data.getTitle(t);
    }
    
    /*
     * This is a convenience method to ensure that we can get and set the title on the JSP pages without having to 
     * put the RegisterForm in the session. title.id does not work.
     */
    public int getTitleId()
    {
        return title.getId();
    }

    public Privilege getPrivilege()
    {
        return privilege;
    }

    public void setPrivilege(Privilege privilege)
    {
        this.privilege = privilege;
    }
    
    public void setPrivilege(int privilegeId)
    {
        privilege = Data.getPrivilege(privilegeId);
    }
    
    public boolean hasAdminRights()
    {
        if (this.getPrivilege()!=null)
        {
            log.debug("Privilege id = " + this.getPrivilege().getId());
        
            if ((this.getPrivilege().getId()==Constants.ADMIN_USER) || (this.getPrivilege().getId()==Constants.SUPER_USER))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    public String getFullName()
    {
        return firstName + " " + surname;
    }
    
    

    public boolean getReceiveEmailNewsletter()
    {
        return receiveEmailNewsletter;
    }

    public void setReceiveEmailNewsletter(boolean receiveEmailNewsletter)
    {
        this.receiveEmailNewsletter = receiveEmailNewsletter;
         
        if (receiveEmailNewsletter==true)
        {
           setTfReceiveEmailNewsletter(true);
        }
        else
        {
            setTfReceiveEmailNewsletter(false);
        }
    }

    public void setTfReceiveEmailNewsletter(TrueFalse tfReceiveEmailNewsletter)
    {
        this.tfReceiveEmailNewsletter = tfReceiveEmailNewsletter;
    }
    
    private void setTfReceiveEmailNewsletter(boolean bReceiveEmailNewsletter)
    {
        TrueFalse tf = new TrueFalse(bReceiveEmailNewsletter);
        this.setTfReceiveEmailNewsletter(tf);
    }
    
    public TrueFalse getTfReceiveEmailNewsletter()
    {
        return tfReceiveEmailNewsletter;
    }
    
}
