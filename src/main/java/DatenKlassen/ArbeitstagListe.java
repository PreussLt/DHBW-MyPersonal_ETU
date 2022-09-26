package DatenKlassen;

import db.Buchungsdaten;
import db.Einstellungen;
import db.sql_connect;
import db.sql_statment;
import lombok.Data;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
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
      getArbeitstage(getAnzahlArbeitstage(getHeute(),"2000-01-01"));

    }catch (Exception e){
      System.err.println("!ERROR! Folgender Fehler ist aufgetreten: "+e);
    }

  }// Constructor



    private int getAnzahlArbeitstage(String eDatum, String aDatum){
    try {
      if (sql_statment.select_arr(cnf.mb_buchung,"count(*)","WHERE B_M_ID=\'"+mid+"\' AND B_TAG >\'"+aDatum+"\' AND B_TAG <=\'"+eDatum+"\'",con)[0][0].equals(0)) return -1;
      int tage = Integer.parseInt(sql_statment.select_arr(cnf.mb_buchung,"count(*)","WHERE B_M_ID=\'"+mid+"\' AND B_TAG >\'"+aDatum+"\' AND B_TAG <=\'"+eDatum+"\'",con)[0][0]);
//      tage += Integer.parseInt(sql_statment.select_arr(cnf.gleitzeittage,"count(*)","WHERE MG_M_ID=\'"+mid+"\' AND MG_TAG >\'"+aDatum+"\' AND MG_TAG <=\'"+eDatum+"\'",con)[0][0]);
      return tage;
    } catch (Exception e){
      System.err.println("!ERROR! Fehler in der Anzahl Arbeitstag: " + e);
      return -1;
    }
    // get AnzahlArbeitstage

  }// get AnzahlArbeitstage

  private boolean getArbeitstage(int laengeListe){
    System.err.println("*INFO* Länge:"+laengeListe);
    arbeitstage = new Arbeitstag[laengeListe];
    if (!sql_statment.select(cnf.mb_buchung,"*","WHERE B_M_ID=\'"+mid+"\'",con)) return false;
    String[][] arbt_tage = sql_statment.select_arr(cnf.mb_buchung,"B_TAG","WHERE B_M_ID=\'"+mid+"\'",con);
    String[][] gleit_tage = sql_statment.select_arr(cnf.gleitzeittage,"MG_TAG","WHERE MG_M_ID=\'"+mid+"\'", con);
    int count_gleitzzeit =0;
    try {
      for (int i = 0; i < arbeitstage.length;i++){
        if (i<arbt_tage.length){ // Es ist kein Gleitzeittag
          arbeitstage[i] = bch.getArbeitszeitEintrag(mid,arbt_tage[i][0]);
        } else {
          arbeitstage[i] = istGleitzeittag(mid,gleit_tage[count_gleitzzeit][0]);
          count_gleitzzeit++;
        }

      }
      return true;
    }catch (Exception e){
      System.err.println("!ERROR! Es ist ein Fehler aufgetreten: "+e);
      e.printStackTrace();
      return false;
    }

  }// getArbeitstage

  private Arbeitstag istGleitzeittag(String mid,String tag){
    return new Arbeitstag(tag,-99.00,null,null,null,mid);
  }
  private String getHeute(){
    // Heutiges Datum Formatieren
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime jetzt = LocalDateTime.now();
    return dtf.format(jetzt);
  }// Get Heuter
}//Class
