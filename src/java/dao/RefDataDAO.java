/*
 * RefDataDAO.java
 *
 */

package dao;

import objects.Area;
import objects.Title;
import objects.MonthYear;
import objects.Year;

import objects.Privilege;
import objects.Country;

import java.sql.*;
import java.util.ArrayList;

import db.DBConnection;
import exceptions.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author robb
 */
public class RefDataDAO 
{
    private static Log log = LogFactory.getLog(RefDataDAO.class);
     
    public static String GET_TITLES =
        "SELECT ID, NAME FROM TITLE";

    
    public static String GET_MONTHS =
         "SELECT ID, NAME FROM MONTH";

    public static String GET_YEARS =
         "SELECT ID, NAME FROM YEAR";


    public static String GET_PRIVILEGES =
         "SELECT ID, NAME FROM PRIVILEGE";

    public static String GET_COUNTRIES =
         "SELECT ID, NAME, CODE, AREA_ID FROM COUNTRY WHERE PRIORITY<10 ORDER BY PRIORITY,NAME ASC";

    public static String GET_AREAS =
         "SELECT ID, NAME FROM AREA";
    
    public static ArrayList getTitles()
    {
         PreparedStatement ps = null;
         DBConnection db = new DBConnection();
         ResultSet rs = null; 
         ArrayList titles = new ArrayList();
         
         try
         {
             ps = db.getPreparedStatement(GET_TITLES);
             
             rs = ps.executeQuery();
             while ( rs.next() )
             {
                    Title t = new Title();
                    t.setId(rs.getInt(1));
                    t.setName(rs.getString(2));
                    titles.add(t);
                    //Integer id = new Integer(rs.getInt(1));
                    //String name = rs.getString(2);
                    //titles.put(id, name);
             }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
            
         }
         catch (SQLException sqle)
         {
            log.error(sqle.getMessage());
             
         }
         finally
         {
            try
            {
                if( rs != null )
                {
                    try
                    { 
                        rs.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                db.destroy();
            }          
            catch (Exception npe)
            {
                log.error(npe.getMessage());
            }
           
            return titles;
        }
    }
    
    
    
    public static ArrayList getPrivileges()
    {
         PreparedStatement ps = null;
         DBConnection db = new DBConnection();
         ResultSet rs = null; 
         ArrayList privileges = new ArrayList();
         
         try
         {
             ps = db.getPreparedStatement(GET_PRIVILEGES);
             
             rs = ps.executeQuery();
             while ( rs.next() )
             {
                    Privilege p = new Privilege();
                    p.setId(rs.getInt(1));
                    p.setName(rs.getString(2));
                    privileges.add(p);
             }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
         }
         catch (SQLException sqle)
         {
            log.error(sqle.getMessage());
             
         }
         finally
         {
            try
            {
                if( rs != null )
                {
                    try
                    { 
                        rs.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                db.destroy();
            }          
            catch (Exception npe)
            {
                log.error(npe.getMessage());
            }
           
            return privileges;
        }
    }

    /*
    public static ArrayList getMonths()
    {
         PreparedStatement ps = null;
         DBConnection db = new DBConnection();
         ResultSet rs = null; 
         ArrayList months = new ArrayList();
         
         try
         {
             ps = db.getPreparedStatement(GET_MONTHS);
             
             rs = ps.executeQuery();
             while ( rs.next() )
             {
                    MonthYear m = new MonthYear();
                    m.setId(rs.getInt(1));
                    m.setMonthName(rs.getString(2));
                    months.add(m);
             }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
         }
         catch (SQLException sqle)
         {
            log.error(sqle.getMessage());
             
         }
         finally
         {
            try
            {
                if( rs != null )
                {
                    try
                    { 
                        rs.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                db.destroy();
            }          
            catch (Exception npe)
            {
                log.error(npe.getMessage());
            }
           
            return months;
        }
    }*/
    
    public static ArrayList getYears()
    {
         PreparedStatement ps = null;
         DBConnection db = new DBConnection();
         ResultSet rs = null; 
         ArrayList years = new ArrayList();
         
         try
         {
             ps = db.getPreparedStatement(GET_YEARS);
             
             rs = ps.executeQuery();
             while ( rs.next() )
             {
                    Year y = new Year();
                    y.setId(rs.getInt(1));
                    y.setName(rs.getString(2));
                    years.add(y);
             }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
         }
         catch (SQLException sqle)
         {
            log.error(sqle.getMessage());
             
         }
         finally
         {
            try
            {
               if( rs != null )
                {
                    try
                    { 
                        rs.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                db.destroy();
            }          
            catch (Exception npe)
            {
                log.error(npe.getMessage());
            }
           
            return years;
        }
    }
    
   
      
    public static ArrayList getCountries()
    {
         PreparedStatement ps = null;
         DBConnection db = new DBConnection();
         ResultSet rs = null; 
         ArrayList countries = new ArrayList();
         
         try
         {
             ps = db.getPreparedStatement(GET_COUNTRIES);
             
             rs = ps.executeQuery();
             while ( rs.next() )
             {
                    Country c = new Country(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getInt(4));
                    countries.add(c);
             }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
         }
         catch (SQLException sqle)
         {
            log.error(sqle.getMessage());
             
         }
         finally
         {
            try
            {
                if( rs != null )
                {
                    try
                    { 
                        rs.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                db.destroy();
            }          
            catch (Exception npe)
            {
                log.error(npe.getMessage());
            }
           
            return countries;
        }
    }
    
    public static ArrayList getAreas()
    {
         PreparedStatement ps = null;
         DBConnection db = new DBConnection();
         ResultSet rs = null; 
         ArrayList areas = new ArrayList();
         
         try
         {
             ps = db.getPreparedStatement(GET_AREAS);
             
             rs = ps.executeQuery();
             while ( rs.next() )
             {
                    Area a = new Area(rs.getInt(1),rs.getString(2));
                    areas.add(a);
             }
            rs.close();
            rs = null;
            ps.close();
            ps = null;
            db.destroy();
         }
         catch (SQLException sqle)
         {
            log.error(sqle.getMessage());
             
         }
         finally
         {
            try
            {
                if( rs != null )
                {
                    try
                    { 
                        rs.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                if( ps != null )
                {
                    try
                    {
                        ps.close();
                    }
                    catch( SQLException ignore )
                    {}
                }
                
                db.destroy();
            }          
            catch (Exception npe)
            {
                log.error(npe.getMessage());
            }
           
            return areas;
        }
    }
   
    
}
