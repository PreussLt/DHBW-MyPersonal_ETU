package DatenKlassen;

import db.Buchungsdaten;
import db.Einstellungen;
import db.sql_connect;
import db.sql_statment;

import java.sql.Connection;

public class ArbeitstagListe {
  // Hier die anderen Nötigen Klassen
  private sql_connect sql_conn = new sql_connect();
  private Einstellungen cnf = new Einstellungen();
  private sql_statment sql_statment = new sql_statment();
  private Buchungsdaten bch = new Buchungsdaten();
  private Connection con = sql_conn.extern_connect();
  // Globale Variabeln
  public Arbeitstag[] arbeitstage;
  private String mid;

  public ArbeitstagListe(String mid) {
    this.mid = mid;
    try {
      getArbeitstage(getAnzahlArbeitstage());

    }catch (Exception e){
      System.err.println("!ERROR! Folgender Fehler ist aufgetreten: "+e);
    }

  }// Constructor

  private int getAnzahlArbeitstage(){
    if (sql_statment.select_arr(cnf.mb_buchung,"count(*)","WHERE B_M_ID=\'"+mid+"\'",con)[0][0].equals(0)) return -1;
    return Integer.parseInt(sql_statment.select_arr(cnf.mb_buchung,"count(*)","WHERE B_M_ID=\'"+mid+"\'",con)[0][0]);
  }// get AnzahlArbeitstage
  private boolean getArbeitstage(int laengeListe){
    arbeitstage = new Arbeitstag[laengeListe];
    if (!sql_statment.select(cnf.mb_buchung,"*","WHERE B_M_ID=\'"+mid+"\'",con)) return false;
    String[][] arbt_tage = sql_statment.select_arr(cnf.mb_buchung,"B_TAG","WHERE B_M_ID=\'"+mid+"\'",con);
    try {
      for (int i = 0; i < arbeitstage.length;i++){
        arbeitstage[i] = bch.getArbeitszeitEintrag(mid,arbt_tage[i][0]);
      }
      return true;
    }catch (Exception e){
      System.err.println("!ERROR! Es ist ein Fehler aufgetreten: "+e);
      return false;
    }

  }// getArbeitstage
}//Class