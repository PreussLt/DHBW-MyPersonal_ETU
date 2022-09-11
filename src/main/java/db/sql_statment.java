package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sql_statment {
  private String[][] ausgabeArray;

  // Ausführen eins SQL SELECT Statment mit Consolen Ausgabe
  public boolean select(String tabelle, String ziel, String bedingung, Connection con){

    try {
      // Abfrage aufbauen
      Statement stm = con.createStatement();
      ResultSet rs = stm.executeQuery("select "+ziel+" from "+tabelle+" "+bedingung+";");

      // Ausgabe 0
      while(rs.next()){
        System.out.println(rs.getString(1) + " " +
          rs.getString(2));
      }//END While

      return true;
    } catch (SQLException e) {
      // Fehler
      e.printStackTrace();
      return false;
    }// END try, Catch

  } // SQL Abruf

//Ausführen eines SQL Select Staments mit Array Ausgabe
  public String[][] select_arr(String tabelle, String ziel, String bedingung, Connection con){
    // Daten für Array abfragen.
   int rows = get_numRows(tabelle,bedingung,con);
   int columns = get_numColums(tabelle,con);
   if (rows == -1 | columns ==-1) System.err.println("Fehler in den Zeilen/Spalten"); // Überprüfen ob eine Fehler in den Spalten vorleigt
    ausgabeArray = new String[rows][columns];

    try {
      // Abfrage aufbauen
      Statement stm = con.createStatement();
      ResultSet rs = stm.executeQuery("select "+ziel+" from "+tabelle+" "+bedingung+";");

      // Ausgabe in Array
      int i=0;
      while(rs.next()){
        if(!datenfuellenArray(rs,i)) break;
        i++;
      }//END While

    } catch (SQLException e) {
      // Fehler
      e.printStackTrace();
      return null;
    }// END try, Catch
    return ausgabeArray;
  }// Select Arra

// Ausführen eines Insert Statment
public boolean insert(String tabelle, String[] Daten, Connection con){

  try {
    // Arayy in SQL Daten input
    String sql_daten ="";
    for (int i=0;i<Daten.length;i++){
      if (i==(Daten.length-1)) sql_daten+= "\'"+Daten[i]+"\'";
      else sql_daten+= "\'"+Daten[i]+"\'"+",";
    }


    // Abfrage aufbauen
    Statement stm = con.createStatement();
    String sql_stm = "INSERT INTO "+tabelle+" VALUES (Null,"+sql_daten+");";
    System.out.println("*INFO* Folgendes SQL-Statment wurde ausgeführt:"+sql_stm);
    stm.execute(sql_stm);
    return true;
  } catch (SQLException e) {
    // Fehler
    e.printStackTrace();
    return false;
  }// END try, Catch

} // SQL Insert

  // Hier Folgen die "Verborgenen" Funktionen
  private boolean datenfuellenArray(ResultSet rs, int count){
    String[] ausgabe = new String[ausgabeArray[0].length];
    for (int i=0; i < ausgabe.length ;i++){
      try {
        ausgabe[i] = rs.getString(i+1);
      }catch (Exception e){
        System.err.println("Fehler: "+e);
      }//END Try Catch

    }// END For

    for (int i=0;i < ausgabeArray[0].length;i++){
      ausgabeArray[count][i] = ausgabe[i];
    }// End For
    //System.out.println(java.util.Arrays.toString(ausgabe)); //--> Debuggin Zeile
    return true;
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
      String statment="select count(*) from INFORMATION_SCHEMA.COLUMNS WHERE table_schema='"+Einstellungen.db_name+"' AND table_name='"+tabelle+"';";
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
