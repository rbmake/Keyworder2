/*
 * MG.java
 *
 * Created on 30 January 2007, 06:53
 *
 */

package objects;
import java.io.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import dao.RefDataDAO;
import constants.Constants;
import rb.utils.tools.DateTool;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author robb
 */
public final class Data
{
    
    /** Creates a new instance of MG */
    public Data()
    {
    }
    
    private static Log log = LogFactory.getLog(Data.class);
    private static HashMap map = new HashMap();
    private static ServletContext sc;
    //private static native Properties initProperties(Properties props);

    public static Object get(String key)
    {
        return map.get(key);
    }
    
    public static void set (String key, Object value)
    {
        map.put(key,value);
    }
    
    public static void init(ServletContext servletContext)
    {
            
            sc = servletContext;
            
            getAndSetTitles();

            getAndSetMonths();

            //getAndSetWeeks();

            getAndSetAreas();

            getAndSetCountries();

            //getAndSetDates();
            
            getAndSetPrivileges();

            setTrueFalse();
                        
            //setLocale();
        
    }
    
   private static void getAndSetTitles()
    {
        
        ArrayList titles = RefDataDAO.getTitles();
        map.put(Constants.SERVLET_TITLES, titles);
        sc.setAttribute(Constants.SERVLET_TITLES, titles);
        log.info("Loaded " + titles.size() + " titles");
    }

   private static void getAndSetMonths()
   {
       ArrayList months = new ArrayList();
       int minYear = new Integer(Props.getProperty("minYear"));
       int maxYear = new Integer(Props.getProperty("maxYear"));

       log.info("Min year is " + minYear + ". Max year is " + maxYear);

       for (int i=minYear; i<maxYear; i++)
       {
           months.add(new MonthYear("January", 1, i));
           months.add(new MonthYear("February", 2, i));
           months.add(new MonthYear("March", 3, i));
           months.add(new MonthYear("April", 4, i));
           months.add(new MonthYear("May", 5, i));
           months.add(new MonthYear("June", 6, i));
           months.add(new MonthYear("July", 7, i));
           months.add(new MonthYear("August", 8, i));
           months.add(new MonthYear("September", 9, i));
           months.add(new MonthYear("October", 10, i));
           months.add(new MonthYear("November", 11, i));
           months.add(new MonthYear("December", 12, i));
           
       }
       map.put(Constants.SERVLET_MONTHS, months);
       sc.setAttribute(Constants.SERVLET_MONTHS, months);
       log.info("Loaded " + months.size() + " months");
       MonthYear firstMonth = (MonthYear)months.get(0);
       log.info("First monthYear is " + firstMonth.getId());
   }

   public static MonthYear getMonthYear(int id)
   {
        ArrayList months = (ArrayList)map.get(Constants.SERVLET_MONTHS);
        Iterator i = months.iterator();
        MonthYear m = new MonthYear();
        // Iterate through until find an id
        while (i.hasNext())
        {
            m = (MonthYear)i.next();
            if (m.getId()==id)
            {
                return m;
            }
        }

        return null;
   }

   public static Title getTitle(int id)
   {
       ArrayList titles = (ArrayList)map.get(Constants.SERVLET_TITLES);
   
        Iterator i = titles.iterator();
        Title t = new Title();
        while ((i.hasNext()) && (t.getId()!=id))
        {
            t = (Title)i.next();
        }
        return t;
    }
   
   /*
    *Retrieves areas from the database and sets them in MGData and ServletContext
    *
    *@param
    *@return
    */ 
   private static void getAndSetAreas()
    {
        ArrayList areas = RefDataDAO.getAreas();
        map.put(Constants.SERVLET_AREAS, areas);
        sc.setAttribute(Constants.SERVLET_AREAS, areas);
        log.info("Loaded " + areas.size() + " areas");
    }
    
   /*
    *Returns an area from an id.
    *
    *@param id Id of the country
    *@return area Area object (id and name)
    */ 
    public static Area getArea(int id)
    {
        ArrayList areas = (ArrayList)map.get(Constants.SERVLET_AREAS);
        Iterator i = areas.iterator();
        Area a = new Area(0, "");
        while ((i.hasNext()) && (a.getId()!=id))
        {
            a = (Area)i.next();
        }
        return a;
    }
   
   /*
    *Retrieves countries from the database and sets them in MGData and ServletContext
    *
    *@param
    *@return
    */ 
   private static void getAndSetCountries()
    {
        ArrayList countries = RefDataDAO.getCountries();
        map.put(Constants.SERVLET_COUNTRIES, countries);
        sc.setAttribute(Constants.SERVLET_COUNTRIES, countries);
        log.info("Loaded " + countries.size() + " countries");
    }
    
   /*
    *Returns a country from an id.
    *
    *@param id Id of the country
    *@return country Country object (id and name)
    */ 
    public static Country getCountry(int id)
    {
        ArrayList countries = (ArrayList)map.get(Constants.SERVLET_COUNTRIES);
        Iterator i = countries.iterator();
        Country c = new Country(0, "", "",0);
        while ((i.hasNext()) && (c.getId()!=id))
        {
            c = (Country)i.next();
        }
        return c;
    }
    
    public static String getCountryName(int id)
    {
        Country c = getCountry(id);
        return c.getName();
    }
    
   
/*
    private static void getAndSetDates()
    {
        // Dates
        ArrayList months = RefDataDAO.getMonths();
       map.put(Constants.SERVLET_MONTHS, months);
       sc.setAttribute(Constants.SERVLET_MONTHS, months);
             
        ArrayList years = RefDataDAO.getYears();
        map.put(Constants.SERVLET_YEARS, years);
        sc.setAttribute(Constants.SERVLET_YEARS, years);
        log.info("Loaded " + months.size() + " months and " + years.size() + " years");
        
        ArrayList startYears = new ArrayList();
        ArrayList expiryYears = new ArrayList();
        
        DateTool dt = new DateTool();
        
        try
        {
            // Get the "Select year"
            startYears.add(0,years.get(0));
            expiryYears.add(0,years.get(0));
        }
        catch (IndexOutOfBoundsException e)
        {
            log.fatal("Table YEARS not loaded");
        }
        
        int i=1;
                
        while (i<years.size())
        {
            Year year = (Year)years.get(i);
            if (year.getYearAsInt() < DateTool.getCurrentYearAsInt()) 
            {
                startYears.add(year);
            }
            else if (year.getYearAsInt() == DateTool.getCurrentYearAsInt()) 
            {
                startYears.add(year);
                expiryYears.add(year);
            }
            else
            {
                expiryYears.add(year);
            }
            i++;
        }
        
        sc.setAttribute(Constants.SERVLET_START_YEARS, startYears);
        sc.setAttribute(Constants.SERVLET_EXPIRY_YEARS, expiryYears);
        map.put(Constants.SERVLET_START_YEARS, startYears);
        map.put(Constants.SERVLET_EXPIRY_YEARS, expiryYears);
    }*/
    
    private static void getAndSetPrivileges()
    {
        ArrayList privileges = RefDataDAO.getPrivileges();
        map.put(Constants.SERVLET_PRIVILEGES, privileges);
        sc.setAttribute(Constants.SERVLET_PRIVILEGES, privileges);
        log.info("Loaded " + privileges.size() + " privileges");
    }
    
    public static Privilege getPrivilege(int id)
    {
        ArrayList privileges = (ArrayList)map.get(Constants.SERVLET_PRIVILEGES);
        Iterator i = privileges.iterator();
        Privilege c = new Privilege();
        // Iterate through until find an id
        while (i.hasNext())
        {
            c = (Privilege)i.next();
            if (c.getId()==id)
            {
                return c;
            }
        }
        
        c.setId(Constants.NOT_SET);
        return c;
    }
    
    private static void setTrueFalse()
    {
        ArrayList truefalse = new ArrayList();
        truefalse.add (new TrueFalse(true));
        truefalse.add (new TrueFalse(false));
        
        map.put(Constants.SERVLET_TRUE_FALSE, truefalse);
        
        sc.setAttribute(Constants.SERVLET_TRUE_FALSE, truefalse);
        log.info("Loaded " + truefalse.size() + " true & false statuses");
    }

    
    /*
    public static void setLocale(Locale l)
    {
        map.put(Constants.LOCALE, l);
    }
    
    public static Locale getLocale()
    {
        return (Locale)map.get(Constants.LOCALE);
    }
        
    private static void setLocale()
    {
        // Set the locale
        setLocale(new Locale(MG.getProperty(Constants.KEY_LOCALE_2_LETTER_LANGUAGE_CODE), MG.getProperty(Constants.KEY_LOCALE_2_LETTER_COUNTRY_CODE)));
        
    }*/
}
