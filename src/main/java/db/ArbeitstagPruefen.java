package db;

import ch.qos.logback.core.joran.conditional.IfAction;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArbeitstagPruefen {
  private final sql_connect sql_conn = new sql_connect();
  private final sql_statment sql = new sql_statment();
  private final Einstellungen cnf = new Einstellungen();

  public double sindPausenEingehalten(String[] zEintrag, double Arbeitszeit){  // gibt für true 0 zurücl
    // TODO: 19.09.2022 Umprogramieren
    if(zEintrag.length==2 && Arbeitszeit <= cnf.erstePause) return 0;
    else if(zEintrag.length==2) return Arbeitszeit-cnf.erstePause;
    double abezugPause=0;

    // Bei 4 Zeiteinträgen
    if (zEintrag.length==4){
      // ERste Arbeitszeit über 6 Stunden
      abezugPause = getAbezugPause(zEintrag, abezugPause);
      if ((Arbeitszeit - abezugPause) > cnf.zweitePause) abezugPause+= cnf.längeZPause;
      // Deifferenz zwischen Drittem und Zweiten Zwiteintrag zngleich der Pausenzeit
      return abezugPause;
    }

    // Bei 6 Zeiteinträgem
    if (zEintrag.length==6){
      // ERste Arbeitszeit über 6 Stunden
      abezugPause = getAbezugPause(zEintrag, abezugPause);
      if((Arbeitszeit-abezugPause)>9) abezugPause += getDifTime(stringToTS(zEintrag[0]),stringToTS(zEintrag[2])) - cnf.längeEPause;
      else if (getDifTime(stringToTS(zEintrag[0]),stringToTS(zEintrag[1])) <= cnf.erstePause && getDifTime(stringToTS(zEintrag[1]),stringToTS(zEintrag[2])) < cnf.längeEPause) abezugPause += cnf.längeEPause - getDifTime(stringToTS(zEintrag[1]),stringToTS(zEintrag[2]));
      // Deifferenz zwischen Drittem und Zweiten Zwiteintrag zngleich der Pausenzeit
      return abezugPause;
    }
    return abezugPause;
  }

  private double getAbezugPause(String[] zEintrag, double abezugPause) {
    if(getDifTime(stringToTS(zEintrag[0]),stringToTS(zEintrag[1])) > cnf.erstePause) abezugPause += getDifTime(stringToTS(zEintrag[0]),stringToTS(zEintrag[2])) - cnf.längeEPause;
    else if (getDifTime(stringToTS(zEintrag[0]),stringToTS(zEintrag[1])) <= cnf.erstePause && getDifTime(stringToTS(zEintrag[1]),stringToTS(zEintrag[2])) < cnf.längeEPause) abezugPause += cnf.längeEPause - getDifTime(stringToTS(zEintrag[1]),stringToTS(zEintrag[2]));
    return abezugPause;
  }


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

  public double sindZeiteneingehalten(String[] zEintrag, Timestamp tMin,Timestamp tMax){ // gibt für true 0 zurück
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
    double ausgabe = (diffInMinutes-stunden*60)*100/60.0;
    ausgabe = ausgabe/100 + (double) stunden;
    //System.out.println(ausgabe); Debugg
    return ausgabe;
  }//

}
