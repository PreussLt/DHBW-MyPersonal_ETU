package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class sql_connect {
  // Standard Daten:
  private static String db = Einstellungen.db; //Datenbank
  private static String db_name = Einstellungen.db_name; // Datenbankname
  private static String db_user = Einstellungen.db_user; // Standartd Datenbank Nutzer
  private static String db_pw = Einstellungen.db_pw; // Standard Datenbank Passwort



  // ETU Zugriff Datenbank
  public static Connection etu_connect(){
    db_user = Einstellungen.db_user_etu; //! Achtung @Ovverreide
    db_pw= Einstellungen.db_pw_etu; //! Achtung @Ovverreide
    //Baut Verbindung zur Datenbank auf und überprüft die Verbindung
    return connect();
  } // etu connect

  // System Zugriff auf Datenbank
  public static Connection intern_connect(){
    db_user = Einstellungen.db_user_intern;
    db_pw = Einstellungen.db_pw_intern;
    //Baut Verbindung zur Datenbank auf und überprüft die Verbindung
    return connect();
  }

  // System Zugriff auf Datenbank
  public static Connection extern_connect(){
    db_user = Einstellungen.db_user_extern;
    db_pw = Einstellungen.db_pw_extern;
    //Baut Verbindung zur Datenbank auf und überprüft die Verbindung
    return connect();
  }


private static Connection connect(){
  try{
    Connection con= DriverManager.getConnection(
      db,db_user,db_pw);
    Statement stmt=con.createStatement();
    ResultSet rs=stmt.executeQuery("show databases;");
    return con;

    //System.out.println("Connected"); --< Debug Gründe
  }//Try

  catch(Exception e){
    System.err.println("Fehler: "+e);
    return null;
  }//Catch

}// connect

}// END Class
