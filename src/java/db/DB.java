/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package db;
import java.sql.*;


/**
 *
 * @author robbrown
 */
public class DB {

Connection connection;
Statement statement;





   public DB()
   {
      try
      {
          Class.forName("com.mysql.jdbc.Driver");
          makeConnection();
      }
      catch (ClassNotFoundException cnfe)
      {
          cnfe.printStackTrace();
      }
      catch (SQLException sqle)
      {
          sqle.printStackTrace();
      }
   }

   public void makeConnection() throws SQLException {
      connection=
         DriverManager.getConnection("jdbc:mysql://localhost:3306/ranking", "root", "scooby");
   }

   public PreparedStatement prepareStatement(String sql) throws SQLException {
      PreparedStatement ps = connection.prepareStatement(sql);
      return ps;
   }

   public void destroy() throws SQLException
   {

       if (connection!=null)
       {
            this.connection.close();
            this.connection=null;
       }

   }

   void displayResults(ResultSet rs) throws SQLException {
      ResultSetMetaData metaData = rs.getMetaData();
      int columns=metaData.getColumnCount();
      String text="";

      while(rs.next()){
         for(int i=1;i<=columns;++i) {
            text+="<"+metaData.getColumnName(i)+">";
            text+=rs.getString(i);
             text+="</"+metaData.getColumnName(i)+">";
             text+="n";
         }
         text+="n";
      }

      System.out.println(text);

   }


}
