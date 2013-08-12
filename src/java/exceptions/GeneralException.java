/*
 * MGException.java
 *
 * Created on 25 October 2006, 22:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package exceptions;
import java.lang.Exception;
import objects.Message;
import constants.Constants;
/**
 *
 * @author robb
 */
public class GeneralException extends Exception {
    
    private String text;
    private Message em;
    private int errorCode;
    private String error;
    
    /** Creates a new instance of MGException */
    public GeneralException(String msg) 
    {
        this.text=msg;
        this.em = new Message(msg);
    }

    public GeneralException(String error, String msg)
    {
        this.em = new Message(msg);
        this.error=error;
    }

    /**
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the em
     */
    public Message getEm() {
        return em;
    }

    /**
     * @param em the em to set
     */
    public void setEm(Message em) {
        this.em = em;
    }

    /*
    public GeneralException(String method, int errorCode)
    {
        String msg = MG.getProperty(method + "." + errorCode);
        if (msg==null)
        {
            msg = MG.getProperty(method + ".default");
        }
        
        if (msg==null)
        {
            msg = MG.getProperty(MGConstants.DEFAULT_ERROR_MESSAGE_KEY, MGConstants.DEFAULT_ERROR_MESSAGE);
        }
        this.text=msg;
        this.em = new MGMessage(msg);
    }
    
    public String getText() {
        return text;
    }
    
    public MGMessage getMGErrorMessage()
    {
        return em;
    }*/
    
}
