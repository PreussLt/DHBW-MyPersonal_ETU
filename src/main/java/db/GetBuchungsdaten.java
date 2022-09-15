package db;

import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetBuchungsdaten {
  private sql_connect sql_conn = new sql_connect();
  private Nutzerverwaltung nzv = new Nutzerverwaltung();
  private Einstellungen cnf = new Einstellungen();
  private sql_statment sql = new sql_statment();

  public Integer getArbeitszeitHeute(String mid){
    Connection con = sql_conn.extern_connect();
    if(!sql.select(cnf.mb_buchung,"*","WHERE B_TAG=\'"+getHeute()+"\' AND B_M_ID=\'"+mid+"\' ",con)) return 0;

    return -1;
  }// get ArbeitszeitHeute

  private double berechneArbeitszeit(String mid,String Tag){
    Connection con = sql_conn.extern_connect();
    if (!nzv.existiertNutzer(mid)) return -1;
    String[][] arbzeit = sql.select_arr(cnf.mb_zeiteintrag+","+cnf.mb_buchung,"*","B_ID = BZ_B_ID AND B_M_ID=\'"+mid+"\' ORDER BY BZ_Zeiteintrag ASC ",con);
    if((arbzeit.length%2)==1){
      System.err.println("!ERROR! Es fehlt ein Zeiteintrag");
      return -1;
    }// END IF, Es ist eine Ungerade Zahl
    double Arbeitszeit = 0;
    for (int i = 0; i>= (arbzeit.length/2);i=i+2){
      try {
        Arbeitszeit = getDifTime(arbzeit[i][5],arbzeit[i+1][0]);
      } catch (Exception e){
        System.err.println("!ERROR! Es enstand ein Fehler beim berechnen entstanden");
        return -1;
      }

    }
    return Arbeitszeit;
  }

  private String getHeute(){
    // Heutiges Datum Formatieren
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime jetzt = LocalDateTime.now();
    String d_heute = dtf.format(jetzt);
    return d_heute;
  }// Get Heuter

  public double getDifTime(String eintrag1, String eintrag2) throws ParseException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    LocalDateTime dateTime1= LocalDateTime.parse(eintrag1, formatter);
    LocalDateTime dateTime2= LocalDateTime.parse(eintrag2, formatter);

    long diffInMinutes = java.time.Duration.between(dateTime1, dateTime2).toMinutes();
    int stunden = (int)diffInMinutes/60;
    double ausgabe = (diffInMinutes-stunden*60)*100/60;
    ausgabe = ausgabe/100 + (double) stunden ;
    return ausgabe;
  }//
}
