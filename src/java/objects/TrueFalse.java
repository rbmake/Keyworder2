/*
 * ItemCategory.java
 *
 */

package objects;
import java.io.Serializable;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author robb
 */
public class TrueFalse implements Serializable
{

    private boolean trueFalse;
    private String yesNo;
    private static Log log = LogFactory.getLog(TrueFalse.class);
    
    public TrueFalse()
    {
        
    }
    public TrueFalse(boolean b)
    {
        setTrueFalse(b);
        
    }

    public boolean isTrueFalse()
    {
        return trueFalse;
    }

    public void setTrueFalse(boolean trueFalse)
    {
        this.trueFalse = trueFalse;
        if (trueFalse==false)
        {
            setYesNo("No");
        }
        else
        {
            setYesNo("Yes");
        }
        
        
    }
    
    public String getYesNo()
    {
        return yesNo;
    }

    public void setYesNo(String yesNo)
    {
        this.yesNo = yesNo;
    }
}
