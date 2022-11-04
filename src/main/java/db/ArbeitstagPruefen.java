package db;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonSerialize
public class ArbeitstagPruefen {
  private final sql_statment sql = new sql_statment();

  public double sindPausenEingehalten(String[][] zEintrag, boolean azubi,double arbeitszeit){  // gibt für true 0 zurücl
    double erstePause;
    double zweitepause;
    double längeEPause;
    double längeZPause;

    if (azubi){
      erstePause=4.5;
      längeEPause=1;
    }else {
      erstePause= Einstellungen.erstePause;
      längeEPause= Einstellungen.laengeEPause;
      zweitepause = Einstellungen.zweitePause;
      längeZPause= Einstellungen.laengeZPause;
    }
    double tmp1,tmp2; // Temporäre variablen
    // Pausen Berechnung für den Azub
    if (azubi){

      if (zEintrag.length==2){
        if (arbeitszeit>erstePause){
          if (arbeitszeit>(erstePause+längeEPause)) return längeEPause; // Volle Länge wird Abegzogen
          else return längeEPause-(arbeitszeit-längeEPause); // Nur Anteilige Länge Wird Berechnet
        }// Die Erste Pausenzeit wurde überschritten
        else return 0; // Die Pausenzeit wurde nicht überschritten
      }// Es sind nur Zwei Einträge voranden

      if (zEintrag.length==4){
        if (arbeitszeit>erstePause){
          tmp1 =+ getDifTime(stringToTS(zEintrag[1][0]),stringToTS(zEintrag[2][0]));
          if (tmp1 >= längeEPause) return 0; // Es wurde genung Pause gemacht
          else {
            tmp2=getDifTime(stringToTS(zEintrag[0][0]),stringToTS(zEintrag[1][0]));
            if (tmp2>erstePause+längeEPause) return längeEPause;
            else return längeEPause-(tmp2-längeEPause);
          }
        }//Pausenzeit wurde überschritten
        else return 0; // Keine Pausenzeit wurde überschritten
      }//Es sind Vier Einträge Vorhanden
    }// Ende Azubi Berechnung
    else { // Kein Azubi
      if (zEintrag.length==2){
        if (arbeitszeit>erstePause){
          if (arbeitszeit>(erstePause+längeEPause)) return längeEPause; // Volle Länge wird Abegzogen
          else return längeEPause-(arbeitszeit-längeEPause); // Nur Anteilige Länge Wird Berechnet
        }// Die Erste Pausenzeit wurde überschritten
        else return 0; // Die Pausenzeit wurde nicht überschritten
      }// Es sind nur Zwei Einträge voranden
      else if (zEintrag.length==4){
        tmp2 = getDifTime(stringToTS(zEintrag[0][0]),stringToTS(zEintrag[1][0]));
        if (tmp2>erstePause){
          tmp1 = getDifTime(stringToTS(zEintrag[1][0]),stringToTS(zEintrag[2][0]));
          if (tmp1 >= längeEPause) return 0; // Es wurde genung Pause gemacht
          else {
            tmp2=getDifTime(stringToTS(zEintrag[0][0]),stringToTS(zEintrag[1][0]));
            if (tmp2>erstePause+längeEPause) return längeEPause;
            else return längeEPause-(tmp2-längeEPause);
          }
        }//Pausenzeit wurde überschritten
      }
      else if (zEintrag.length==6){

      }
    }
    return 0;
  }// Ende Classe

  private double getAbezugPause(String[] zEintrag, double abezugPause) {
    if(getDifTime(stringToTS(zEintrag[0]),stringToTS(zEintrag[1])) > Einstellungen.erstePause) abezugPause += getDifTime(stringToTS(zEintrag[0]),stringToTS(zEintrag[2])) - Einstellungen.laengeEPause;
    else if (getDifTime(stringToTS(zEintrag[0]),stringToTS(zEintrag[1])) <= Einstellungen.erstePause && getDifTime(stringToTS(zEintrag[1]),stringToTS(zEintrag[2])) < Einstellungen.laengeEPause) abezugPause += Einstellungen.laengeEPause - getDifTime(stringToTS(zEintrag[1]),stringToTS(zEintrag[2]));
    return abezugPause;
  }


  public  boolean istTagFeiertag(String tag,Connection con){
    return sql.select(Einstellungen.feiertag, "f_tag", "WHERE f_tag='" + tag + "'", con);
  }// ISt der Tag ein Feiertag

  public boolean istTagUrlaubstag(String tag, String mid, Connection con){
    return sql.select(Einstellungen.gleitzeittage, "MG_TAG", "WHERE  MG_TAG='" + tag + "'AND MG_Urlaub=\'1\' AND MG_M_ID='" + mid + "'", con);
  }// Ist der Tag ein Urlaubstag tag?

  public boolean istTagGleitzeitag(String tag, String mid, Connection con){
    return sql.select(Einstellungen.gleitzeittage, "MG_TAG", "WHERE  MG_TAG='" + tag + "'AND MG_Urlaub=\'0\' AND MG_M_ID='" + mid + "'", con);
  }// Ist der Tag ein Gleitzeit tag?

  public double sindZeiteneingehalten(String[][] zEintrag, Timestamp tMin,Timestamp tMax){ // gibt für true 0 zurück
    double abzugZeit=0;
    if (stringToTS(zEintrag[0][0]).before(tMin)) abzugZeit += getDifTime(stringToTS(zEintrag[0][0]),tMin);
    if (stringToTS(zEintrag[zEintrag.length-1][0]).after(tMax)) abzugZeit+= getDifTime(tMax,stringToTS(zEintrag[(zEintrag.length-1)][0]));
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
