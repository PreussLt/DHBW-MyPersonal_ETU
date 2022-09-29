package db;

import DatenKlassen.ArbeitstagListe;

import java.sql.Connection;

public class Freietage {
  // Andere Klassen
  private static sql_statment sql = new sql_statment();
  private static Connection con = sql_connect.intern_connect();
  private static Einstellungen cnf = new Einstellungen();


  public double getGleitzeitstand(ArbeitstagListe ab){
    double ausgabe = 0;
    for (int i=0; i<ab.arbeitstage.length;i++){
        ausgabe += ab.arbeitstage[i].arbeitszeit - ab.arbeitstage[i].sollArbeitszeit;
    }
    return ausgabe;
  }// GetGleitzeitstand

  public boolean setGleitzeittag(String tag,String mid){
    try {
      String[] Daten = {mid,"0",tag};
      return sql.insert(cnf.gleitzeittage,Daten,con);
    }catch (Exception e){
      System.err.println("!ERROR! Fehler in setGleitzeittag: "+e);
      return false;
    }
  }// setGleitzeitata

  public boolean setUrlaubstag(String tag,String mid){
    try {
      String[] Daten = {mid,"1",tag};
      return sql.insert(cnf.gleitzeittage,Daten,con);
    }catch (Exception e){
      System.err.println("!ERROR! Fehler in setUrlaubstag: "+e);
      return false;
    }
  }// setGleitzeitata

}// Class
