/*
 * Message.java
 *
 */

package objects;
//import java.io.Serializable;
/**
 *
 * @author robb
 */
public class Message
{
    
    private String text;
    private int id;
    
    public Message()
    {
        
    }
    
    public Message( String s)
    {
        this.setText(s);
    }
    
    public Message( int id, String text)
    {
        setId(id);
        setText(text);
    }

    public boolean equals(String compareString)
    {
        if (getText().compareTo(compareString)==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}
