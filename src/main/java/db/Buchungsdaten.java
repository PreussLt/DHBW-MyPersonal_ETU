package db;

import DatenKlassen.Arbeitstag;
import DatenKlassen.TimeEntry;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Buchungsdaten {
  private final Nutzerverwaltung nzv = new Nutzerverwaltung();
  private final sql_statment sql = new sql_statment();
  private final Buchung bch = new Buchung();


  public Arbeitstag getArbeitszeiteintrag(String mid,String tag){
    Connection con = sql_connect.extern_connect();
    if (!nzv.existiertNutzer(mid)) return null;
    String eStempel, lStempel;
    double stunden;
    String[][] stempel;

    stunden = berechneArbeitszeit(mid,tag); // TODO: 19.09.2022  hier gegen get Austauschen

    if (!bch.ueberpruefeBuchungvorhanden(tag,mid,con)) return null;
    String bid = sql.select_arr(Einstellungen.mb_buchung,"B_ID","WHERE B_M_ID='"+mid+"' AND B_TAG='"+tag+"'",con)[0][0];
    stempel = sql.select_arr(Einstellungen.mb_zeiteintrag,"*","WHERE BZ_B_ID='"+bid+"'",con);
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
    Connection con = sql_connect.extern_connect();
    if(!sql.select(Einstellungen.mb_buchung,"B_Tag","WHERE B_M_ID='"+mid+"' AND B_TAG='"+getHeute()+"'",con)){
      String[] daten ={mid,getHeute(),"-99"};
      if (!sql.insert(Einstellungen.mb_buchung,daten,con)) return false;
    }
    String bid = sql.select_arr(Einstellungen.mb_buchung,"B_ID","WHERE B_M_ID='"+mid+"' AND B_TAG='"+getHeute()+"'",con)[0][0];
    String[] daten ={bid,getTimestamp()};
    if (!sql.insert(Einstellungen.mb_zeiteintrag,daten,con)) return false;
    return true;
  }
  public boolean setZeitintrag(String mid, String timestampp){
    Connection con = sql_connect.extern_connect();
    String tag = getDatumVonTimestamp(timestampp);
    if(!sql.select(Einstellungen.mb_buchung,"B_Tag","WHERE B_M_ID='"+mid+"' AND B_TAG='"+tag+"'",con)){
      String[] daten ={mid,tag,"-99"};
      if (!sql.insert(Einstellungen.mb_buchung,daten,con)) return false;
    }
    String bid = sql.select_arr(Einstellungen.mb_buchung,"B_ID","WHERE B_M_ID='"+mid+"' AND B_TAG='"+tag+"'",con)[0][0];
    String[] daten ={bid,timestampp};
    if (!sql.insert(Einstellungen.mb_zeiteintrag,daten,con)) return false;
    return true;
  }


  public Arbeitstag getArbeitszeitEintrag(String mid, String tag){
    Connection con = sql_connect.extern_connect();
    double arbeitszeit = getArbeitszeit(mid, tag);
    String eStemp,lStemp;
    String[][] sql_abf = sql.select_arr(Einstellungen.mb_zeiteintrag+","+Einstellungen.mb_buchung,"*"," WHERE B_ID = BZ_B_ID AND B_M_ID='"+mid+"' AND B_Tag='"+tag+"' ORDER BY BZ_Zeiteintrag ASC ",con);
    return null;
  }

  public ArrayList<TimeEntry> getAllTimeentries(String bid){
    ArrayList<TimeEntry> entries = new ArrayList<>();
    try{
      Connection con = sql_connect.intern_connect();
      String bedingungen = String.format("WHERE BZ_B_ID = %d ORDER BY `bz_zeitsteintrag`.`BZ_Zeiteintrag` ASC", Integer.parseInt(bid));
      ResultSet rs = sql.fetchAll("bz_zeitsteintrag", bedingungen, con);

      while(rs.next()){
        String zid = String.valueOf(rs.getInt(1));
        String timestamp = String.valueOf(rs.getTimestamp(3));
        TimeEntry t = new TimeEntry(zid, bid, timestamp);
        entries.add(t);
      }
    } catch(SQLException e){
      e.printStackTrace();
    }
    return entries;
  }



  public double getArbeitszeit(String mid){
    Connection con = sql_connect.extern_connect();
    if(!sql.select(Einstellungen.mb_buchung,"*","WHERE B_TAG='"+getHeute()+"' AND B_M_ID='"+mid+"' ",con)) return 0;
    if(Double.parseDouble(sql.select_arr(Einstellungen.mb_buchung,"B_Stunden","WHERE B_TAG='"+getHeute()+"' AND B_M_ID='"+mid+"' ",con)[0][0])== -99.00) return berechneArbeitszeit(mid,getHeute());
    if(!sql.select(Einstellungen.mb_buchung,"B_Stunden","WHERE B_TAG='"+getHeute()+"' AND B_M_ID='"+mid+"' ",con)) return berechneArbeitszeit(mid,getHeute());
    else return Double.parseDouble(sql.select_arr(Einstellungen.mb_buchung,"B_Stunden","WHERE B_TAG='"+getHeute()+"' AND B_M_ID='"+mid+"' ",con)[0][0]);
  }// get ArbeitszeitHeute

  public double getArbeitszeit(String mid, String tag){
    Connection con = sql_connect.extern_connect();
    if(!sql.select(Einstellungen.mb_buchung,"*","WHERE B_TAG='"+tag+"' AND B_M_ID='"+mid+"' ",con)) return 0;
    if(Double.parseDouble(sql.select_arr(Einstellungen.mb_buchung,"B_Stunden","WHERE B_TAG='"+tag+"' AND B_M_ID='"+mid+"' ",con)[0][0])==-99.00) return berechneArbeitszeit(mid,tag);
    if(!sql.select(Einstellungen.mb_buchung,"B_Stunden","WHERE B_TAG='"+tag+"' AND B_M_ID='"+mid+"' ",con)) return berechneArbeitszeit(mid,tag);
    else return Double.parseDouble(sql.select_arr(Einstellungen.mb_buchung,"B_Stunden","WHERE B_TAG='"+tag+"' AND B_M_ID='"+mid+"' ",con)[0][0]);
  }// get Arbeitszeit AM Tag X

  private double berechneArbeitszeit(String mid,String Tag){
    Connection con = sql_connect.extern_connect();
    if (!nzv.existiertNutzer(mid)) return -1;
    String[][] arbzeit = sql.select_arr(Einstellungen.mb_zeiteintrag+","+Einstellungen.mb_buchung,"*"," WHERE B_ID = BZ_B_ID AND B_M_ID='"+mid+"' AND B_Tag='"+Tag+"' ORDER BY BZ_Zeiteintrag ASC ",con);
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
    sql.update(Einstellungen.mb_buchung,ziel,wert,"WHERE B_M_ID='"+mid+"' AND B_Tag='"+Tag+"'",con);
    return Arbeitszeit;
  }

  public String getDatumVonTimestamp(String timestamp){
    Timestamp t = stringToTS(timestamp);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate tag = t.toLocalDateTime().toLocalDate();
    return dtf.format(tag);
  }
  private String getHeute(){
    // Heutiges Datum Formatieren
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime jetzt = LocalDateTime.now();
    return dtf.format(jetzt);
  }// Get Heuter

  private String getTimestamp(){
    // Heutiges Datum Formatieren
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime jetzt = LocalDateTime.now();
    return dtf.format(jetzt);
  }// Get Heuter

  public double getDifTime(String eintrag1, String eintrag2){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    LocalDateTime dateTime1= LocalDateTime.parse(eintrag1, formatter);
    LocalDateTime dateTime2= LocalDateTime.parse(eintrag2, formatter);

    long diffInMinutes = java.time.Duration.between(dateTime1, dateTime2).toMinutes();
    int stunden = (int)diffInMinutes/60;
    double ausgabe = (diffInMinutes-stunden*60)*100/60.0;
    ausgabe = ausgabe/100 + (double) stunden;
    //System.out.println(ausgabe); Debugg
    return ausgabe;
  }//

  private Timestamp stringToTS(String s){
    return Timestamp.valueOf(s);
  }
}
