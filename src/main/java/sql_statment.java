import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sql_statment {


  // Ausführen eins SQL SELECT Statment mit Consolen Ausgabe
  public void select(String tabelle, String ziel, String bedingung, Connection con){

    try {
      // Abfrage aufbauen
      Statement stm = con.createStatement();
      ResultSet rs = stm.executeQuery("select "+ziel+" from "+tabelle+" "+bedingung+";");

      // Ausgabe 0
      while(rs.next()){
        System.out.println(rs.getString(1) + " " +
          rs.getString(2));
      }//END While


    } catch (SQLException e) {
      // Fehler
      e.printStackTrace();
    }// END try, Catch

  } // SQL Abruf

//Ausführen eines SQL Select Staments mit Array Ausgabe
  public String[][] select_arr(String tabelle, String ziel, String bedingung, Connection con){
    // Daten für Array abfragen.
   int rows = get_numRows(tabelle,bedingung,con);
   int columns = get_numColums(tabelle,con);
   if (rows == -1 | columns ==-1) System.err.println("Fehler in den Zeilen/Spalten"); // Überprüfen ob eine Fehler in den Spalten vorleigt
    String[][] ausgabeArray = new String[rows][columns];

    try {
      // Abfrage aufbauen
      Statement stm = con.createStatement();
      ResultSet rs = stm.executeQuery("select "+ziel+" from "+tabelle+" "+bedingung+";");

      // Ausgabe in Array
      int i=0;
      while(rs.next()){
        datenfüllenArray(rs,ausgabeArray);
      }//END While

    } catch (SQLException e) {
      // Fehler
      e.printStackTrace();
    }// END try, Catch

    return ausgabeArray;
  }

  // Hier Folgen die "Verborgenen" Funktionen
  public String[] datenfüllenArray(ResultSet rs,String[][] ausgabeArray){
    String[] ausgabe = new String[ausgabeArray[0].length];
    for (int i=0; i < ausgabe.length ;i++){
      try {
        ausgabe[i] = rs.getString(i+1);
      }catch (Exception e){
        System.err.println("Fehler: "+e);
      }//END Try Catch

    }// END For
    System.out.println(java.util.Arrays.toString(ausgabe));
    return ausgabe;
  }// datenfüllen Array

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
      // Anzahl der Rows herraus finden
      Statement stm = con.createStatement();
      String statment="select count(*) from INFORMATION_SCHEMA.COLUMNS WHERE table_schema='"+sql_connect.db_name+"' AND table_name='"+tabelle+"';";
      ResultSet rs = stm.executeQuery(statment);
      rs.next();
      int count = rs.getInt(1);
      return count;

    } catch (Exception e){
      return -1;
    }// END try, catch
  }// gent_numRows

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
