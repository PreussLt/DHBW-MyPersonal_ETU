package db;

import DatenKlassen.BuchungModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Buchung {
  private final sql_statment sql = new sql_statment();

// Neuer Zeiteintrag für dne Heutigen Tag
  public boolean neuerZeiteintrag(String mID){
    // NEue Datenbnak Verbindung
    Connection con = sql_connect.intern_connect();
    if (!ueberpruefeBuchungvorhanden(getHeute(),mID,con)){
      System.err.println("!ERROR! Buchung für heute existiert nicht");
      return false;
    }else {
      // Die Buchungs ID Für Heute Erhalten
      String sBedingung = "WHERE B_M_ID='" + mID + "'  AND B_TAG='" + getHeute() + "' LIMIT 1";
      String[][] tmpArr = sql.select_arr(Einstellungen.mb_buchung, "*", sBedingung, con);
      String b_id = tmpArr[0][0];
      //Neuen Zeiteintrag Hinzufügen
      String[] daten = {b_id, getZeiteintragHeute()};
      sql.insert(Einstellungen.mb_zeiteintrag,daten,con);
      return true;
    }
  }

// Neuer Zeiteintrag für speziellen Tag
public boolean neuerZeiteintrag(String mID,String tag,String Zeitstempel){
  // NEue Datenbnak Verbindung
  Connection con = sql_connect.intern_connect();
  if (!ueberpruefeBuchungvorhanden(tag,mID,con)){
    System.err.println("!ERROR! Buchung für heute existiert nicht");
    return false;
  }else {
    // Die Buchungs ID Für Heute Erhalten
    String sBedingung = "WHERE B_M_ID='" + mID + "'  AND B_TAG='" + tag + "' LIMIT 1";
    String[][] tmpArr = sql.select_arr(Einstellungen.mb_buchung, "*", sBedingung, con);
    String b_id = tmpArr[0][0];
    //Neuen Zeiteintrag Hinzufügen
    String[] daten = {b_id, Zeitstempel};
    sql.insert(Einstellungen.mb_zeiteintrag,daten,con);
    return true;
  }
}

  // Buchung für den Heutigen Tag
  public boolean neueBuchung(String mID) {
    // NEue Datenbnak Verbindung
    Connection con = sql_connect.intern_connect();

    boolean flag = ueberpruefeBuchungvorhanden(getHeute(),mID,con); // Falg für Bucung nicht schon Vorhanden

    if (flag) System.out.println("*INFO* Buchung war schon vorhanden");
    else {
          // SQL ÜBertragung Vorbereiten
          String heute = getHeute();
          String[] daten = {mID,heute};
      return sql.insert(Einstellungen.mb_buchung, daten, con);
    }// ELSE


      return true;
  }// Neue Bucung

  // Neue Buchung für speziellen Tag
  public boolean neueBuchung(String mID,String tag) {
    // NEue Datenbnak Verbindung
    Connection con = sql_connect.intern_connect();

    boolean flag = ueberpruefeBuchungvorhanden(tag,mID,con); // Falg für Bucung nicht schon Vorhanden

    if (flag) System.out.println("*INFO* Buchung war schon vorhanden");
    else {
      // SQL ÜBertragung Vorbereiten
      String[] daten = {mID,tag};
      return sql.insert(Einstellungen.mb_buchung, daten, con);
    }// ELSE


    return true;
  }// Neue Buchung

  public boolean loescheBuchung(String bid){
    // NEue Datenbnak Verbindung
    Connection con = sql_connect.intern_connect();
    Boolean zeiteintraege = sql.select(Einstellungen.mb_zeiteintrag,"*","BZ_B_ID='"+bid+"'",con);
    System.out.println("r");
    if (!sql.delete(Einstellungen.mb_zeiteintrag,"BZ_B_ID='"+bid+"'",con)) return false;

    return sql.delete(Einstellungen.mb_buchung, "B_ID='" + bid + "'", con);


  }// lösche Buchung und damit auch die Zeiteinträge

  public boolean loescheZeiteintraege(String bz_id){
    Connection con = sql_connect.intern_connect();
    return sql.delete(Einstellungen.mb_zeiteintrag, "BZ_B_ID='" + bz_id + "'", con);
  }

  public ArrayList<BuchungModel> getAllBuchungen(String mid){
    ArrayList<BuchungModel> buchungen = new ArrayList<>();
      Connection con = sql_connect.intern_connect();
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
  public boolean ueberpruefeBuchungvorhanden(String Date, String mID, Connection con){
    // Überprüfen ob für den Heutigen Tag schon eine Buchung Besteht
    String sBedingung = "WHERE B_M_ID='" + mID + "'  AND B_TAG='" + Date + "'";
    //System.out.println(sBedingung);// --> Debuggin
    String[][] tmpArr = sql.select_arr(Einstellungen.mb_buchung, "*", sBedingung, con);
    // Überprüfen ob Buchung doppelt, mehrfach oder schon vorhanden ist
    if (tmpArr.length>1){
      System.err.println("!Error! Doppelte Buchung, Prozess wird vorgesetzt");
      return true;
    }// END IF
    else if (tmpArr.length==1){
      System.out.println("*Info* Buchung vorhanden");
      return true;
    } else return false;

  }// ÜberprüfeBuchungsvorhande

  private String getHeute(){
    // Heutiges Datum Formatieren
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime jetzt = LocalDateTime.now();
    return dtf.format(jetzt);
  }// Get Heuter

  private String getZeiteintragHeute(){
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
    LocalDateTime jetzt = LocalDateTime.now();
    return dtf.format(jetzt);
  } //getZeinteintragHeute;
}// Buchung
