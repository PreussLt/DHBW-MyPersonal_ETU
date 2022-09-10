import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sql_statment {


  // Ausführen eins SQL SELECT Statment
  public void sql_select(String tabelle, String ziel, String bedingung, Connection con){

    try {
      // Abfrage aufbauen
      Statement stm = con.createStatement();
      ResultSet rs = stm.executeQuery("select "+ziel+" from "+tabelle+" "+bedingung+";");

      // Ausgabe 0
      while(rs.next()){
        System.out.println(rs.getString(1) + " " +
          rs.getString(2));
      }//END While

      System.out.println("Anzahl der Zeilen: "+get_numRows(tabelle,bedingung,con));

    } catch (SQLException e) {
      // Fehler
      e.printStackTrace();
    }// END try, Catch

  } // SQL Abruf


  // Hier Folgen die "Verborgenen" Funktionen

  public int get_numRows(String tabelle, String bedingung, Connection con){
    try {
      // Anzahl der Rows herraus finden
      Statement stm = con.createStatement();
      ResultSet rs = stm.executeQuery("select count(*) from "+tabelle+" "+bedingung+";");
      rs.next();
      int count = rs.getInt(1);
      return count;

    } catch (Exception e){
      return -1;
    }// END try, catch
  }// get_numRows

  public int get_numColums(String tabelle, Connection con){
    try {
      System.err.println("ffff");
      // Anzahl der Rows herraus finden
      Statement stm = con.createStatement();

      ResultSet rs = stm.executeQuery("select count(*) from INFORMATION_SCHEMA.COLUMNS WHERE table_catalog="+sql_connect.db_name+" AND table_name="+tabelle+";");
      System.err.println("f "+rs);
      rs.next();
      int count = rs.getInt(1);
      return count;

    } catch (Exception e){
      return -1;
    }// END try, catch
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
