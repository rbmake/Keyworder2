
package objects;
import java.io.Serializable;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 *
 * @author robb
 */
public class MonthYear implements Serializable
{
    private static Log log = LogFactory.getLog(MonthYear.class);


    // The id is a combination of year then month
    private int id;
    private int month;
    private String monthName;
    private String name;
    private int year;
    
    
    public MonthYear()
    {
    }

    public MonthYear(int id,String name)
    {
        this.id=id;
        this.monthName=name;
    }

    public MonthYear(int month, int year)
    {
        this.month=month;
        this.year=year;
        this.id=new Integer(new Integer(month).toString() + new Integer(year).toString());
    }

    public MonthYear(String name, int month, int year)
    {
        this.monthName=name;
        this.month=month;
        this.year=year;
        this.id=new Integer(new Integer(month).toString() + new Integer(year).toString());
    }

    /**
     * This constructor can only take in monthYear strings in format mmYYYY
     * e.g. 032010
     * @param monthYear
     */
    /*
    public Month(String monthYear)
    {
        log.debug("Substring 0,1 of monthYear is " + monthYear.substring(0, 2));
        
        this.id=new Integer(monthYear.substring(0, 2));
        this.year=new Integer(monthYear.substring(2));
    }*/

    public MonthYear(int id)
    {
        this.id=id;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getMonthName()
    {
        return monthName;
    }

    public void setMonthName(String name)
    {
        this.monthName = name;
    }

    public String getName()
    {
        return (monthName + " " + year);
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }
    
}
