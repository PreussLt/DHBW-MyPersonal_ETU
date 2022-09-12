package db;

import java.sql.Connection;

public class Nutzerverwaltung {
  // Loader
  Einstellungen cnf = new Einstellungen();
  sql_connect sql_conn = new sql_connect();
  sql_statment sql = new sql_statment();
  Passwort_verwaltung pwv = new Passwort_verwaltung();

  public boolean existiertNutzer(String vNname, String nName){
    Connection con = sql_conn.intern_connect();
    if(!sql.select(cnf.mitarbeiter,"*","WHERE M_Vorname=\'"+vNname+"\' AND M_Nachname=\'"+nName+"\'",con) ) return false;
    System.err.println("x");
    return true;
  }
  public boolean existiertNutzer(String mid){
    Connection con = sql_conn.intern_connect();
    if(!sql.select(cnf.mitarbeiter,"*","WHERE M_ID=\'"+mid+"\'",con) ) return false;
    return true;
  }
  public boolean passwort_ändern(String mId,String neuPassword){
    try {
      // Neue Passwort
      String neuSalt = pwv.get_Salt();
      if (!pwv.pw_richtlinen_check(neuPassword)) return false;
      String neuPW = pwv.get_hash(neuPassword,neuSalt);
      // Vorbereitung Arrays
      String ziele[]= {"M_Passwort","M_Salt"};
      String wert[]={neuPW,neuSalt};
      String bedingung ="WHERE M_ID=\'"+mId+"\'";
      // Neu Verbindung Aufbauen
      Connection con = sql_conn.intern_connect();
      // Statment ausführen
      if(!sql.update(cnf.mitarbeiter,ziele,wert,bedingung,con)) return false;
      return true;
    }catch (Exception e){
      // Fehlermeldung
      System.err.println("!ERROR! passwort_ändern"+e);
      return false;
    }// END Try catch
  }// passwort ändern

  // Bestehnden Nutzer Löschen
  public boolean nutzer_löschen(String mID){
    try {
      Connection con = sql_conn.intern_connect();
      if (!sql.delete(cnf.mitarbeiter,"WHERE M_ID=\'"+mID+"\'",con)) return false;
      if (!sql.delete(cnf.mb_konto,"WHERE MK_M_ID=\'"+mID+"\';",con)) return false;
      // TODO: 11.09.2022 SAobald Bucungen Hinzugeügt wurden Hier erweitern
      return true;
    }catch (Exception e){
      System.err.println("!ERROR! Folgende Fehler Meldung wurde Ausgebene: "+e);
      return false;
    }//END try Catc
  }//Nutzer Löschen

  // Neuen Nutzer Anlegen
  public boolean nutzer_anlegen(String vName,String nName,int pNummer,String pw,int aModell, int uKlasse){
      try {
        // Datenbank Verbindung aufbauen
        Connection con = sql_conn.intern_connect();

        // Übeprüfen ob eingaben Korrekt
        if (!überprüfeZahlen(aModell,uKlasse,con)) return false;
        if (!pwv.pw_richtlinen_check(pw)) return false;

        // Passwort aufbereiten
        String pw_salt = new Passwort_verwaltung().get_Salt();
        pw = pwv.get_hash(pw,pw_salt);

        // Neue Daten in Datenbank Hinzufügen
        String[] Daten = {vName,nName,Integer.toString(pNummer),pw,pw_salt};
        if (!sql.insert(cnf.mitarbeiter,Daten,con)) return false;

        // Neue ID erhalten
        String sqlBedingung = "WHERE`M_Personalnummer`=\'"+pNummer+"\'ORDER BY M_ID DESC LIMIT 1;";
        String[][] getID = sql.select_arr(cnf.mitarbeiter,"*",sqlBedingung,con);
        String newID = getID[0][0];
        //System.out.println(newID);  // --> Debugging

        // MK_Konto Tabelle Füllen
        String[] Daten2 ={newID, Integer.toString(uKlasse),Integer.toString(aModell)};
        if(!sql.insert(cnf.mb_konto,Daten2,con)) return false;

        con.close();
        return true;
      }catch (Exception e){
        System.err.println("Fehler: "+e);
        return false;
      }// END try catch
  }// Nutzer anlegen

  private boolean überprüfeZahlen(int aModell, int uKlasse, Connection con){
    Boolean flag=false;

    // Überprüfen ob Modell Existiert
    String[][] arr_aModell = sql.select_arr(cnf.mb_arbeitsmodell,"*","",con);
    for (int i = 0; i< arr_aModell.length;i++){
      if (aModell == Integer.parseInt(arr_aModell[i][0])) flag=true;
    }//END For
    if (!flag) return false;

    // Überprüfen ob Userklasse Exsitiert
    flag = false;
    // Überprüfen ob Modell Existiert
    String[][] arr_uKlasse = sql.select_arr(cnf.mb_konto,"*","",con);
    for (int i = 0; i< arr_uKlasse.length;i++){
      if (uKlasse == Integer.parseInt(arr_aModell[i][0])) flag=true;
    }// END FOR
    if (!flag) return false;

    return true;
  }// ÜberprüfeZahlen


}// Nuterverwaltung
