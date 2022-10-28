package DatenKlassen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import db.ArbeitstagPruefen;
import db.Einstellungen;
import db.sql_connect;
import db.sql_statment;
import lombok.Data;
import org.threeten.extra.YearWeek;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class Arbeitstag {
  @JsonIgnore
  private ArbeitstagPruefen aTag = new ArbeitstagPruefen();
  @JsonIgnore
  private static Einstellungen cnf = new Einstellungen();
  @JsonIgnore
  private static Connection con = sql_connect.intern_connect();
  @JsonIgnore
  private static sql_statment sql = new sql_statment();


  public String ersterStempel;
  public String letzterStempel;
  public String mid;
  private int calendarWeek;
  private int calendarYear;

  // Public Boolens
  public String tag;
  public double arbeitszeit;
  public String[][] zeitstempel;



  // Boolens
  public boolean feiertag = false;
  public boolean gleitzeittag = false;
  public boolean urlaubstag = false;
  public boolean arbeitszeitenEingehalten;
  public boolean pausenEingehalten;
  public boolean maxArbeitszeitEingehalten;

  //private double
  private double maxArbeitszeit=10;
  public double sollArbeitszeit;


  public Arbeitstag(String tag, double arbeitszeit, String[][] zeitstempel, String ersterStempel, String letzterStempel, String mid) {

    try {
      this.tag = tag;
      this.arbeitszeit = arbeitszeit;
      this.zeitstempel = zeitstempel;
      this.ersterStempel = ersterStempel;
      this.letzterStempel = letzterStempel;
      this.mid = mid;
      this.sollArbeitszeit = getSollarbeitszeit();
      this.maxArbeitszeit = getMaxArbeitszeit();
      this.calendarWeek = getCalendarWeek(tag);
      this.calendarYear = getCalendarYear(tag);

      if (this.zeitstempel != null){
        vorgabenAnwenden();
      } else {
        // Überpüfe ob Tag Feiertag oder Gleitzeittag ist
        feiertag = aTag.istTagFeiertag(this.tag,con);
        urlaubstag = aTag.istTagUrlaubstag(this.tag,mid,con);
        if (!urlaubstag){
          gleitzeittag = aTag.istTagGleitzeitag(this.tag, mid, con);
        }
        if (feiertag || gleitzeittag || urlaubstag ) this.arbeitszeit = sollArbeitszeit;
      }
    }catch (Exception e){
      System.err.println("!ERROR! Fehler im Arbeitstag Constructor: "+e);
    }


  }// Constructor

  public void vorgabenAnwenden() {
    // Überpüfe ob Tag Feiertag oder Gleitzeittag ist
    feiertag = aTag.istTagFeiertag(this.tag, con);
    gleitzeittag = aTag.istTagGleitzeitag(this.tag, mid, con);
    if (feiertag || gleitzeittag) arbeitszeit = Sollarbeitszeit();
    arbeitszeitenEingehalten = ZeitGrenzen();
    pausenEingehalten = sindPausenEingehalten();
    maxArbeitszeitEingehalten = istMaxArbeitszeitEingehalten();
    System.out.println(arbeitszeitenEingehalten);

  }


  private boolean sindPausenEingehalten() {
    return true;
  }

  private boolean istMaxArbeitszeitEingehalten() {
    if (arbeitszeit > maxArbeitszeit) {
      arbeitszeit = maxArbeitszeit;
      return false;
    }
    return true;
  }

  private boolean ZeitGrenzen() {
    try {
      String[][] res_sql = sql.select_arr(cnf.mb_konto+", "+cnf.mb_arbeitsmodell+"","a_arbeitsmodelle.A_Starzeit, a_arbeitsmodelle.A_Endzeit","WHERE MK_A_ID=A_ID AND MK_M_ID=\""+mid+"\"",con);
      String Min = tag +" "+res_sql[0][0]+":00:00"; //
      String Max = tag +" "+ res_sql[0][1]+":00:00"; // ""
      Timestamp tMin = Timestamp.valueOf(Min);
      Timestamp tMax = Timestamp.valueOf(Max);

      if (aTag.sindZeiteneingehalten(getZeitstempel(), tMin, tMax) == 0) return true;
      System.err.println("*INFO* Warscheinlich der Fehler");
      this.arbeitszeit = arbeitszeit - aTag.sindZeiteneingehalten(getZeitstempel(), tMin, tMax);
      return false;
    }catch (Exception e){
      System.err.println("Fehler in ABT.ZeitGrenzen: "+e);
      return false;
    }

  }

  private double getMaxArbeitszeit(){
    try {
      if (!sql.select(""+Einstellungen.mb_konto+","+Einstellungen.mb_arbeitsmodell+","+Einstellungen.mitarbeiter+","+Einstellungen.mb_grenzwerte,"G_TAG"," WHERE M_ID = MK_M_ID AND MK_A_ID = A_ID AND A_G_ID = G_ID AND M_ID='"+mid+"'",con)) {
        System.err.println("!ERROR! Fehler in getMaxarbeitszeit: Keine Zeit Angegeben");
        return -1;
      }// End If
      // Wert Vorhanden
      return Double.parseDouble(sql.select_arr(""+Einstellungen.mb_konto+","+Einstellungen.mb_arbeitsmodell+","+Einstellungen.mitarbeiter+","+Einstellungen.mb_grenzwerte,"G_TAG"," WHERE M_ID = MK_M_ID AND MK_A_ID = A_ID AND A_G_ID = G_ID AND M_ID='"+mid+"'",con)[0][0]);
    }catch (Exception e){
      System.err.println("!ERROR! Fehler in getMaxarbeitszeit: "+e);
      return -1;
    }// try Catc
  }// End getMAxArbeitszeit

  private double getSollarbeitszeit(){
    try {
      if (!sql.select(""+Einstellungen.mb_konto+","+Einstellungen.mb_arbeitsmodell+","+Einstellungen.mitarbeiter+","+Einstellungen.mb_grenzwerte,"A_Sollstunden/A_Solltage"," WHERE M_ID = MK_M_ID AND MK_A_ID = A_ID AND A_G_ID = G_ID AND M_ID='"+mid+"'",con)) {
        System.err.println("!ERROR! Fehler in getMaxarbeitszeit: Keine Zeit Angegeben");
        return -1;
      }// End If
      // Wert Vorhanden
      return Double.parseDouble(sql.select_arr(""+Einstellungen.mb_konto+","+Einstellungen.mb_arbeitsmodell+","+Einstellungen.mitarbeiter+","+Einstellungen.mb_grenzwerte,"A_Sollstunden/A_Solltage"," WHERE M_ID = MK_M_ID AND MK_A_ID = A_ID AND A_G_ID = G_ID AND M_ID='"+mid+"'",con)[0][0]);
    }catch (Exception e){
      System.err.println("!ERROR! Fehler in getMaxarbeitszeit: "+e);
      return -1;
    }// try Catc
  }// End get sollarbeitszeit

  private int getCalendarWeek(String date){
    LocalDate localDate = LocalDate.parse(date);
    return Integer.parseInt(YearWeek.from(localDate).toString().substring(6));
  }

  private int getCalendarYear(String date){
    LocalDate localDate = LocalDate.parse(date);
    return Integer.parseInt(YearWeek.from(localDate).toString().substring(0,4));
  }

  private double Sollarbeitszeit() {
    return this.sollArbeitszeit;
  }
}
