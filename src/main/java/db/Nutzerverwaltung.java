package db;

import DatenKlassen.User;

import java.sql.Connection;

public class Nutzerverwaltung {
  // Loader
  sql_statment sql = new sql_statment();
  Passwort_verwaltung pwv = new Passwort_verwaltung();

  public User getUser(String pnummer){
    Connection con = sql_connect.intern_connect();
    if (!sql.select(Einstellungen.mitarbeiter,"*","WHERE M_Personalnummer='"+pnummer+"'",con)){
      System.err.println("!ERROR! Personalnummer exisitert nicht");
      return null;
    }
    String[][] arr = sql.select_arr(Einstellungen.mitarbeiter,"*","WHERE M_Personalnummer='"+pnummer+"'",con);
    return setUserdata(arr);
  }

  public User getUserMid(String mid){
    Connection con = sql_connect.intern_connect();
    if (!sql.select(Einstellungen.mitarbeiter,"*", "WHERE M_ID='" +mid+ "'",con)){
      System.err.println("!ERROR! ID exisitert nicht");
      return null;
    }
    String[][] arr = sql.select_arr(Einstellungen.mitarbeiter,"*", "WHERE M_ID='" +mid+ "'",con);
    return setUserdata(arr);
  }

  private User setUserdata(String[][] arr) {
    User user = new User();
    user.setId(arr[0][0]);
    user.setPrename(arr[0][1]);
    user.setLastname(arr[0][2]);
    user.setPrsnumber(arr[0][3]);
    user.setAbtnumber(arr[0][4]);
    user.setPasshash(arr[0][5]);
    user.setSalt(arr[0][6]);
    return user;
  }


  public boolean existiertNutzer(String vNname, String nName,Connection con){
    if(!sql.select(Einstellungen.mitarbeiter,"*","WHERE M_Vorname='"+vNname+"' AND M_Nachname='"+nName+"'",con) ) return false;
    System.err.println("x");
    return true;
  }
  public boolean existiertNutzer(String mid, Connection con){
    if(!sql.select(Einstellungen.mitarbeiter,"*","WHERE M_ID='"+mid+"'",con) ) {
      System.err.println("!ERROR! Nutzer existiert nicht");
      return false;
    }
    return true;
  }
  public boolean existiertNutzerMitPersonalnummer(String personalnummer, Connection con){
    if(!sql.select(Einstellungen.mitarbeiter,"*","WHERE M_PERSONALNUMMER='"+personalnummer+"'",con) ) {
      System.err.println("!ERROR! Nutzer existiert nicht");
      return false;
    }
    return true;
  }
  public boolean passwort_aendern(String mId,String neuPassword, Connection con){
    try {
      // Neue Passwort
      String neuSalt = pwv.get_Salt();
      if (!pwv.pw_richtlinen_check(neuPassword)) return false;
      String neuPW = pwv.get_hash(neuPassword,neuSalt);
      // Vorbereitung Arrays
      String[] ziele = {"M_Passwort","M_Salt"};
      String[] wert ={neuPW,neuSalt};
      String bedingung ="WHERE M_ID='"+mId+"'";

      // Statment ausführen
      return sql.update(Einstellungen.mitarbeiter, ziele, wert, bedingung, con);
    }catch (Exception e){
      // Fehlermeldung
      System.err.println("!ERROR! passwort_ändern"+e);
      return false;
    }// END Try catch
  }// passwort ändern

  // Bestehnden Nutzer Löschen
  public boolean nutzer_loeschen(String mID,Connection con){
    try {
      if (!sql.delete(Einstellungen.mitarbeiter,"WHERE M_ID='"+mID+"'",con)) return false;
      return sql.delete(Einstellungen.mb_konto, "WHERE MK_M_ID='" + mID + "';", con);
      // TODO: 11.09.2022 SAobald Bucungen Hinzugeügt wurden Hier erweitern
    }catch (Exception e){
      System.err.println("!ERROR! Folgende Fehler Meldung wurde Ausgebene: "+e);
      return false;
    }//END try Catc
  }//Nutzer Löschen

  // Neuen Nutzer Anlegen
  public boolean nutzer_anlegen(String vName,String nName,int pNummer,String pw,int aModell, int uKlasse,String gDatum, Connection con){
      try {
       
        // Übeprüfen ob eingaben Korrekt
        if (!ueberpruefeZahlen(aModell,uKlasse,con)) return false;
        if (!pwv.pw_richtlinen_check(pw)) return false;

        // Passwort aufbereiten
        String pw_salt = new Passwort_verwaltung().get_Salt();
        pw = pwv.get_hash(pw,pw_salt);

        // Abteilungs ID Bei änderungen hier hinzufügen
        String aID = "1";

        // Neue Daten in Datenbank Hinzufügen
        String[] Daten = {vName,nName,Integer.toString(pNummer),aID,pw,pw_salt,gDatum};
        if (!sql.insert(Einstellungen.mitarbeiter,Daten,con)) return false;

        // Neue ID erhalten
        String sqlBedingung = "WHERE`M_Personalnummer`='"+pNummer+"'ORDER BY M_ID DESC LIMIT 1;";
        String[][] getID = sql.select_arr(Einstellungen.mitarbeiter,"*",sqlBedingung,con);
        String newID = getID[0][0];
        //System.out.println(newID);  // --> Debugging

        // MK_Konto Tabelle Füllen
        String[] Daten2 ={newID, Integer.toString(uKlasse),Integer.toString(aModell)};
        if(!sql.insert(Einstellungen.mb_konto,Daten2,con)) return false;

        con.close();
        return true;
      }catch (Exception e){
        System.err.println("Fehler: "+e);
        return false;
      }// END try catch
  }// Nutzer anlegen

  private boolean ueberpruefeZahlen(int aModell, int uKlasse, Connection con){
    boolean flag=false;

    // Überprüfen ob Modell Existiert
    String[][] arr_aModell = sql.select_arr(Einstellungen.mb_arbeitsmodell,"*","",con);
    for(String[] strings : arr_aModell) {
      if(aModell == Integer.parseInt(strings[0])) flag = true;
    }//END For
    if (!flag) return false;

    // Überprüfen ob Userklasse Exsitiert
    flag = false;
    // Überprüfen ob Modell Existiert
    String[][] arr_uKlasse = sql.select_arr(Einstellungen.mb_konto,"*","",con);
    for (int i = 0; i< arr_uKlasse.length;i++){
      if (uKlasse == Integer.parseInt(arr_aModell[i][0])) flag=true;
    }// END FOR
    return flag;
  }// ÜberprüfeZahlen


}// Nuterverwaltung
