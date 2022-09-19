package db;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArbeitstagPruefen {
  private  sql_connect sql_conn = new sql_connect();
  private  sql_statment sql = new sql_statment();
  private  Einstellungen cnf = new Einstellungen();

  public  boolean istTagFeiertag(String tag){
    Connection con = sql_conn.extern_connect();
    if (!sql.select(cnf.feiertag,"f_tag","WHERE f_tag=\'"+tag+"\'",con)) return false;
    return true;
  }// ISt der Tag ein Feiertag

  public boolean istTagGleitzeitag(String tag, String mid){
    Connection con = sql_conn.extern_connect();
    if (!sql.select(cnf.gleitzeittage,"MG_TAG","WHERE  MG_TAG=\'"+tag+"\' AND MG_M_ID=\'"+mid+"\'",con)) return false;
    return true;
  }// Ist der Tag ein Gleitzeit tag?

  public double sindZeiteneingehalten(String[] zEintrag, Timestamp tMin,Timestamp tMax){
    double abzugZeit=0;
    if (stringToTS(zEintrag[0]).before(tMin)) abzugZeit += getDifTime(stringToTS(zEintrag[0]),tMin);
    if (stringToTS(zEintrag[3]).after(tMax)) abzugZeit+= getDifTime(tMax,stringToTS(zEintrag[(zEintrag.length-1)]));
    return abzugZeit;
  }

  private Timestamp stringToTS(String s){
    return Timestamp.valueOf(s);
  }
  private double getDifTime(Timestamp d1, Timestamp d2) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    LocalDateTime dT1= d1.toLocalDateTime();
    LocalDateTime dT2= d2.toLocalDateTime();
    long diffInMinutes = java.time.Duration.between(dT1, dT2).toMinutes();
    int stunden = (int)diffInMinutes/60;
    double ausgabe = (diffInMinutes-stunden*60)*100/60;
    ausgabe = ausgabe/100 + (double) stunden;
    //System.out.println(ausgabe); Debugg
    return ausgabe;
  }//

}
