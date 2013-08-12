/*
 * 
 */

package objects;
import java.util.Date;

/**
 *
 * @author robbrown
 */
public class Request {

    private Long id;
    private Date date;

    public Request()
    {

    }

    public Request(Long id, Date date)
    {
        setId(id);
        setDate(date);
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

}
