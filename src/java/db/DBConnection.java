/*
 * DBConnection.java
 *
 * Created on 22 October 2006, 07:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package db;


import java.sql.*;
import javax.sql.DataSource;
import javax.naming.NamingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author robb
 */
public class DBConnection {
    
    public Connection myConnection;
    private DataSource dataSource;
    private static Log log = LogFactory.getLog(DBConnection.class);
    
    /** Creates a new instance of DBConnection */
    public DBConnection() 
    {
      
        try
        {
            dataSource = getPoolDB();
            myConnection = dataSource.getConnection();
        }
        catch (NamingException e)
        {
            log.fatal(e.getMessage());
        }
        catch (SQLException sqe)
        {
            log.fatal(sqe.getMessage());
        }
    }
     
    
    public Connection getConnection()
    {
        return this.myConnection;
    }
    
    public PreparedStatement getPreparedStatement(String sqlString)
    throws SQLException
    {
        Connection con = null;
        PreparedStatement st = null;
        
        try
        {
            con = this.getConnection();

            if (con != null)
            {
                st = con.prepareStatement(sqlString);
                
            }
        }
        finally
        {
            return st;
        }
        
    }
    
    public void close(ResultSet rs){
        
        if(rs !=null){
            try{
               rs.close();
               rs=null;
            }
            catch(Exception e){}
        
        }
    }
    
    public void close(java.sql.Statement stmt){
        
        if(stmt !=null){
            try{
               stmt.close();
               stmt=null;
            }
            catch(Exception e){}
        
        }
    }
     
    public void destroy(){
  
    if(myConnection !=null){
    
         try{
               myConnection.close();
               myConnection=null;
            }
            catch(Exception e){}
        
        
    }
  }

    private javax.sql.DataSource getPoolDB() throws javax.naming.NamingException
    {
        javax.naming.Context c = new javax.naming.InitialContext();
        return (javax.sql.DataSource) c.lookup("java:comp/env/jdbc/poolKeyworder");
    }
    
    private javax.sql.DataSource getDerbyDB() throws javax.naming.NamingException
    {
        javax.naming.Context c = new javax.naming.InitialContext();
        return (javax.sql.DataSource) c.lookup("jdbc:derby://localhost:1527/travel");
    }


}
