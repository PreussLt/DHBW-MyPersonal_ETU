import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sql_statment {

  public void sql_abruf(String tabelle, String ziel, String bedingung, Connection con){

    try {
      Statement stm = con.createStatement();
      ResultSet rs = stm.executeQuery("select "+ziel+" from "+tabelle+" "+bedingung+";");

      while(rs.next()){
        System.out.println(rs.getString(1) + " " +
          rs.getString(2));
      }
      System.out.println("Anzahl der Zeilen: "+res_rows(tabelle,bedingung,con));

    } catch (SQLException e) {
      e.printStackTrace();
    }

  } // SQL Abruf

  public int res_rows(String tabelle, String bedingung, Connection con){
    try {
      Statement stm = con.createStatement();
      ResultSet rs = stm.executeQuery("select count(*) from "+tabelle+" "+bedingung+";");
      rs.next();
      int count = rs.getInt(1);
      return count;

    } catch (Exception e){
      return -1;
    }
  }

  /*
  public void testabruf(Connection con){
    try {
      Statement stm = con.createStatement();
      ResultSet rs = stm.executeQuery("select * from uk_userklassen ;");


      while(rs.next()){
        System.out.println(rs.getString(1) + " " +
          rs.getString(2));
      }

    } catch (Exception e){
      System.err.println("Fehler: "+e);
    }

  } */

}
