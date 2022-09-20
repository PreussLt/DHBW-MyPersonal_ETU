package db;

import DatenKlassen.Arbeitstag;

import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Buchungsdaten {
  private sql_connect sql_conn = new sql_connect();
  private Nutzerverwaltung nzv = new Nutzerverwaltung();
  private Einstellungen cnf = new Einstellungen();
  private sql_statment sql = new sql_statment();
  private Buchung bch = new Buchung();


  public Arbeitstag getArbeitszeiteintrag(String mid,String tag){
    Connection con = sql_conn.extern_connect();
    if (!nzv.existiertNutzer(mid)) return null;
    String eStempel, lStempel;
    double stunden;
    String[][] stempel;

    stunden = berechneArbeitszeit(mid,tag); // TODO: 19.09.2022  hier gegen get Austauschen

    if (!bch.überprüfeBuchungvorhanden(tag,mid,con)) return null;
    String bid = sql.select_arr(cnf.mb_buchung,"B_ID","WHERE B_M_ID=\'"+mid+"\' AND B_TAG=\'"+tag+"\'",con)[0][0];
    stempel = sql.select_arr(cnf.mb_zeiteintrag,"*","WHERE BZ_B_ID=\'"+bid+"\'",con);
    eStempel = stempel[0][2];
    lStempel=stempel[(stempel.length-1)][2];
    String[] timestamps = new String[stempel.length];
    for (int i=0;i < stempel.length;i++) timestamps[i] = stempel[i][2];
    return new Arbeitstag(tag,stunden,timestamps,eStempel,lStempel,mid);

  }
  public String[][] getArbeitszeitListe() {

    return null;
  }



  public boolean setZeitintrag(String mid){
    Connection con = sql_conn.extern_connect();
    if(!sql.select(cnf.mb_buchung,"B_Tag","WHERE B_M_ID=\'"+mid+"\' AND B_TAG=\'"+getHeute()+"\'",con)){
      String[] daten ={mid,getHeute(),"-1"};
      if (!sql.insert(cnf.mb_buchung,daten,con)) return false;
    }
    String bid = sql.select_arr(cnf.mb_buchung,"B_ID","WHERE B_M_ID=\'"+mid+"\' AND B_TAG=\'"+getHeute()+"\'",con)[0][0];
    String[] daten ={bid,getTimestamp()};
    if (!sql.insertZeiteintrag(cnf.mb_zeiteintrag,daten,con)) return false;
    return true;
  }
  public boolean setZeitintrag(String mid, String timestampp){
    Connection con = sql_conn.extern_connect();
    String tag = getDatumVonTimestamp(timestampp);
    if(!sql.select(cnf.mb_buchung,"B_Tag","WHERE B_M_ID=\'"+mid+"\' AND B_TAG=\'"+tag+"\'",con)){
      String[] daten ={mid,getHeute(),"-1"};
      if (!sql.insert(cnf.mb_buchung,daten,con)) return false;
    }
    String bid = sql.select_arr(cnf.mb_buchung,"B_ID","WHERE B_M_ID=\'"+mid+"\' AND B_TAG=\'"+tag+"\'",con)[0][0];
    String[] daten ={bid,timestampp};
    if (!sql.insertZeiteintrag(cnf.mb_zeiteintrag,daten,con)) return false;
    return true;
  }


  public Arbeitstag getArbeitszeitEintrag(String mid, String tag){
    Connection con = sql_conn.extern_connect();
    double arbeitszeit = getArbeitszeit(mid, tag);
    String eStemp,lStemp;
    String[][] sql_abf = sql.select_arr(cnf.mb_zeiteintrag+","+cnf.mb_buchung,"*"," WHERE B_ID = BZ_B_ID AND B_M_ID=\'"+mid+"\' AND B_Tag=\'"+tag+"\' ORDER BY BZ_Zeiteintrag ASC ",con);
    return null;
  }




  public double getArbeitszeit(String mid){
    Connection con = sql_conn.extern_connect();
    if(!sql.select(cnf.mb_buchung,"*","WHERE B_TAG=\'"+getHeute()+"\' AND B_M_ID=\'"+mid+"\' ",con)) return 0;
    if(Double.parseDouble(sql.select_arr(cnf.mb_buchung,"B_Stunden","WHERE B_TAG=\'"+getHeute()+"\' AND B_M_ID=\'"+mid+"\' ",con)[0][0])== -99.00) return berechneArbeitszeit(mid,getHeute());
    if(!sql.select(cnf.mb_buchung,"B_Stunden","WHERE B_TAG=\'"+getHeute()+"\' AND B_M_ID=\'"+mid+"\' ",con)) return berechneArbeitszeit(mid,getHeute());
    else return Double.parseDouble(sql.select_arr(cnf.mb_buchung,"B_Stunden","WHERE B_TAG=\'"+getHeute()+"\' AND B_M_ID=\'"+mid+"\' ",con)[0][0]);
  }// get ArbeitszeitHeute

  public double getArbeitszeit(String mid, String tag){
    Connection con = sql_conn.extern_connect();
    if(!sql.select(cnf.mb_buchung,"*","WHERE B_TAG=\'"+tag+"\' AND B_M_ID=\'"+mid+"\' ",con)) return 0;
    if(Double.parseDouble(sql.select_arr(cnf.mb_buchung,"B_Stunden","WHERE B_TAG=\'"+tag+"\' AND B_M_ID=\'"+mid+"\' ",con)[0][0])==-99.00) return berechneArbeitszeit(mid,tag);
    if(!sql.select(cnf.mb_buchung,"B_Stunden","WHERE B_TAG=\'"+tag+"\' AND B_M_ID=\'"+mid+"\' ",con)) return berechneArbeitszeit(mid,tag);
    else return Double.parseDouble(sql.select_arr(cnf.mb_buchung,"B_Stunden","WHERE B_TAG=\'"+tag+"\' AND B_M_ID=\'"+mid+"\' ",con)[0][0]);
  }// get Arbeitszeit AM Tag X

  private double berechneArbeitszeit(String mid,String Tag){
    Connection con = sql_conn.extern_connect();
    if (!nzv.existiertNutzer(mid)) return -1;
    String[][] arbzeit = sql.select_arr(cnf.mb_zeiteintrag+","+cnf.mb_buchung,"*"," WHERE B_ID = BZ_B_ID AND B_M_ID=\'"+mid+"\' AND B_Tag=\'"+Tag+"\' ORDER BY BZ_Zeiteintrag ASC ",con);
    if((arbzeit.length%2)==1){
      System.err.println("!ERROR! Es fehlt ein Zeiteintrag");
      return -1;
    }// END IF, Es ist eine Ungerade Zahl
    System.out.println(arbzeit[0].length+" "+arbzeit.length);
    double Arbeitszeit = 0;
    int z1=0;
    int z2=1;
    for (int i = 0; i <(arbzeit.length/2);i++){
      try {
        Arbeitszeit += getDifTime(arbzeit[z1][2],arbzeit[z2][2]);
        z1+=2;
        z2+=2;
      } catch (Exception e){
        System.err.println("!ERROR! Es enstand ein Fehler beim berechnen entstanden: "+e);
        return -1;
      }// END Catcj

    }// END For
    String[] ziel = {"B_Stunden"};
    String[] wert ={Double.toString(Arbeitszeit)};
    sql.update(cnf.mb_buchung,ziel,wert,"WHERE B_M_ID=\'"+mid+"\' AND B_Tag=\'"+Tag+"\'",con);
    return Arbeitszeit;
  }

  private String getDatumVonTimestamp(String timestamp){
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate tag = LocalDate.parse(timestamp, dtf);
    return dtf.format(tag);
  }
  private String getHeute(){
    // Heutiges Datum Formatieren
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime jetzt = LocalDateTime.now();
    String d_heute = dtf.format(jetzt);
    return d_heute;
  }// Get Heuter

  private String getTimestamp(){
    // Heutiges Datum Formatieren
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
    ausgabe = ausgabe/100 + (double) stunden;
    //System.out.println(ausgabe); Debugg
    return ausgabe;
  }//
}
