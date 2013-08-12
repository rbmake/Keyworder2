/*
 * 
 */

package forms;
import org.apache.struts.action.ActionForm;

/**
 *
 * @author robb
 */
public class RequestForm extends ActionForm {
    
    private long requestId;
    private String export;
    
    
    public RequestForm() {
        //
    }

    /**
     * @return the requestId
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * @param requestId the requestId to set
     */
    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    /**
     * @return the export
     */
    public String getExport() {
        return export;
    }

    /**
     * @param export the export to set
     */
    public void setExport(String export) {
        this.export = export;
    }

   

   
    
}
