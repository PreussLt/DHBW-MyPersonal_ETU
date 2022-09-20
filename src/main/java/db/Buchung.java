package db;

import DatenKlassen.BuchungModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Buchung {
  private Einstellungen cnf = new Einstellungen();
  private sql_statment sql = new sql_statment();
  private sql_connect sql_conn = new sql_connect();

// Neuer Zeiteintrag für dne Heutigen Tag
  public boolean neuerZeiteintrag(String mID){
    // NEue Datenbnak Verbindung
    Connection con = sql_conn.intern_connect();
    if (!überprüfeBuchungvorhanden(getHeute(),mID,con)){
      System.err.println("!ERROR! Buchung für heute existiert nicht");
      return false;
    }else {
      // Die Buchungs ID Für Heute Erhalten
      String sBedingung = "WHERE B_M_ID=\'" + mID + "\'  AND B_TAG=\'" + getHeute() + "\' LIMIT 1";
      String[][] tmpArr = sql.select_arr(cnf.mb_buchung, "*", sBedingung, con);
      String b_id = tmpArr[0][0];
      //Neuen Zeiteintrag Hinzufügen
      String[] daten = {b_id, getZeiteintragHeute()};
      sql.insert(cnf.mb_zeiteintrag,daten,con);
      return true;
    }
  }

// Neuer Zeiteintrag für speziellen Tag
public boolean neuerZeiteintrag(String mID,String tag,String Zeitstempel){
  // NEue Datenbnak Verbindung
  Connection con = sql_conn.intern_connect();
  if (!überprüfeBuchungvorhanden(tag,mID,con)){
    System.err.println("!ERROR! Buchung für heute existiert nicht");
    return false;
  }else {
    // Die Buchungs ID Für Heute Erhalten
    String sBedingung = "WHERE B_M_ID=\'" + mID + "\'  AND B_TAG=\'" + tag + "\' LIMIT 1";
    String[][] tmpArr = sql.select_arr(cnf.mb_buchung, "*", sBedingung, con);
    String b_id = tmpArr[0][0];
    //Neuen Zeiteintrag Hinzufügen
    String[] daten = {b_id, Zeitstempel};
    sql.insert(cnf.mb_zeiteintrag,daten,con);
    return true;
  }
}

  // Buchung für den Heutigen Tag
  public boolean neueBuchung(String mID) {
    // NEue Datenbnak Verbindung
    Connection con = sql_conn.intern_connect();

    boolean flag = überprüfeBuchungvorhanden(getHeute(),mID,con); // Falg für Bucung nicht schon Vorhanden

    if (flag) System.out.println("*INFO* Buchung war schon vorhanden");
    else {
          // SQL ÜBertragung Vorbereiten
          String heute = getHeute();
          String[] daten = {mID,heute};
          if (!sql.insert(cnf.mb_buchung,daten,con)) return false;
          return true;
    }// ELSE


      return true;
  }// Neue Bucung

  // Neue Buchung für speziellen Tag
  public boolean neueBuchung(String mID,String tag) {
    // NEue Datenbnak Verbindung
    Connection con = sql_conn.intern_connect();

    boolean flag = überprüfeBuchungvorhanden(tag,mID,con); // Falg für Bucung nicht schon Vorhanden

    if (flag) System.out.println("*INFO* Buchung war schon vorhanden");
    else {
      // SQL ÜBertragung Vorbereiten
      String[] daten = {mID,tag};
      if (!sql.insert(cnf.mb_buchung,daten,con)) return false;
      return true;
    }// ELSE


    return true;
  }// Neue Buchung

  public boolean löscheBuchung(String bid){
    // NEue Datenbnak Verbindung
    Connection con = sql_conn.intern_connect();
    Boolean zeiteintraege = sql.select(cnf.mb_zeiteintrag,"*","BZ_B_ID=\'"+bid+"\'",con);
    if (zeiteintraege != null){
      System.out.println("r");
      if (!sql.delete(cnf.mb_zeiteintrag,"BZ_B_ID=\'"+bid+"\'",con)) return false;
    }

    if(!sql.delete(cnf.mb_buchung,"B_ID=\'"+bid+"\'",con)) return false;
    else return true;


  }// lösche Buchung und damit auch die Zeiteinträge

  public boolean löscheZeiteinträge(String bz_id){
    Connection con = sql_conn.intern_connect();
    if (!sql.delete(cnf.mb_zeiteintrag,"BZ_B_ID=\'"+bz_id+"\'",con)) return false;
    return true;
  }

  public ArrayList<BuchungModel> getAllBuchungen(String mid){
    ArrayList<BuchungModel> buchungen = new ArrayList<>();
      Connection con = sql_conn.intern_connect();
      String bedingungen = String.format("WHERE B_M_ID = %d ORDER BY `b_buchung`.`B_Tag` ASC", Integer.parseInt(mid));
      ResultSet rs = sql.fetchAll("b_buchung", bedingungen, con);
    try {
      while(rs.next()) {
        String buchungsid = String.valueOf(rs.getInt(1));
        String day = String.valueOf(rs.getDate(3));
        String hours = String.valueOf(rs.getDouble(4));
        BuchungModel b = new BuchungModel(buchungsid, mid, day, hours);
        buchungen.add(b);
      }
    }
    catch(SQLException e) {
        throw new RuntimeException(e);
      }
    return buchungen;
  }

  /*
  Hier sin die verstekten Funktionen
   */
  public boolean überprüfeBuchungvorhanden(String Date,String mID,Connection con){
    // Überprüfen ob für den Heutigen Tag schon eine Buchung Besteht
    String sBedingung = "WHERE B_M_ID=\'" + mID + "\'  AND B_TAG=\'" + Date + "\'";
    //System.out.println(sBedingung);// --> Debuggin
    String[][] tmpArr = sql.select_arr(cnf.mb_buchung, "*", sBedingung, con);
    // Überprüfen ob Buchung doppelt, mehrfach oder schon vorhanden ist
    if (tmpArr.length>1){
      System.err.println("!Error! Doppelte Buchung, Prozess wird vorgesetzt");
      return true;
    }// END IF
    else if (tmpArr.length==1){
      System.out.println("*Info* Buchung vorhanden");
      return true;
    } else if (tmpArr.length == 0) return false;
    else return false;

  }// ÜberprüfeBuchungsvorhande

  private String getHeute(){
    // Heutiges Datum Formatieren
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime jetzt = LocalDateTime.now();
    String d_heute = dtf.format(jetzt);
    return d_heute;
  }// Get Heuter

  private String getZeiteintragHeute(){
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    LocalDateTime jetzt = LocalDateTime.now();
    String d_heute = dtf.format(jetzt);
    return d_heute;
  } //getZeinteintragHeute;
}// Buchung
