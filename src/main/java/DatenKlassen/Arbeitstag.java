package DatenKlassen;

import db.ArbeitstagPruefen;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Arbeitstag {
  private ArbeitstagPruefen aTag = new ArbeitstagPruefen();
  private String ersterStempel;
  private String letzterStempel;
  private String mid;

  // Public Boolens
  public String tag;
  public double arbeitszeit;
  public String[] Zeistempel;


  // Boolens
  public boolean feiertag=false;
  public boolean gleitzeittag=false;
  public boolean arbeitszeitenEingehalten;
  public boolean pausenEingehalten;
  public boolean maxArbeitszeitEingehalten;




  public Arbeitstag(String tag, double arbeitszeit, String[] zeistempel, String ersterStempel, String letzterStempel,String mid) {
    this.tag = tag;
    this.arbeitszeit = arbeitszeit;
    Zeistempel = zeistempel;
    this.ersterStempel = ersterStempel;
    this.letzterStempel = letzterStempel;
    this.mid = mid;
    vorgabenAnwenden();
  }// Constructor

  public void vorgabenAnwenden(){
    // Überpüfe ob Tag Feiertag oder Gleitzeittag ist
    feiertag = aTag.istTagFeiertag(this.tag);
    gleitzeittag = aTag.istTagGleitzeitag(this.tag,mid);
    if (feiertag || gleitzeittag) arbeitszeit = Sollarbetiszeit();
    arbeitszeitenEingehalten = ZeitGrenzen();
    pausenEingehalten = sindPausenEingehalten();
    maxArbeitszeitEingehalten = istMaxArbeitszeitEingehalten();
    System.out.println(arbeitszeitenEingehalten);

  }

  private boolean sindPausenEingehalten(){
    return true;
  }
  private boolean istMaxArbeitszeitEingehalten(){
    double maxArbeitszeit = 10; // TODO: 19.09.2022 Aus datenbank ziehen
    if (arbeitszeit > maxArbeitszeit ){
      arbeitszeit=maxArbeitszeit;
      return false;
    }
    return true;
  }
  private boolean ZeitGrenzen(){
    String Min = tag+" 06:00:00"; // TODO: 19.09.2022 Aus datenbank ziehen
    String Max = tag+" 22:00:00"; // ""

    Timestamp tMin = Timestamp.valueOf(Min);
    Timestamp tMax = Timestamp.valueOf(Max);

    if (aTag.sindZeiteneingehalten(getZeistempel(),tMin,tMax) == 0) return true;

    this.arbeitszeit = arbeitszeit - aTag.sindZeiteneingehalten(getZeistempel(),tMin,tMax);
    return false;
  }
  private double Sollarbetiszeit(){
    return 7.6; // TODO: 19.09.2022 Hier die Datenbank verbindung aufbauen und den Berechneten Wert ausgeben
  }




  // Getter und Setter @AutoGenerarated
  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public double getArbeitszeit() {
    return arbeitszeit;
  }

  public void setArbeitszeit(double arbeitszeit) {
    this.arbeitszeit = arbeitszeit;
  }

  public String[] getZeistempel() {
    return Zeistempel;
  }

  public void setZeistempel(String[] zeistempel) {
    Zeistempel = zeistempel;
  }

  public String getErsterStempel() {
    return ersterStempel;
  }

  public void setErsterStempel(String ersterStempel) {
    this.ersterStempel = ersterStempel;
  }

  public String getLetzterStempel() {
    return letzterStempel;
  }

  public void setLetzterStempel(String letzterStempel) {
    this.letzterStempel = letzterStempel;
  }

  public boolean isFeiertag() {
    return feiertag;
  }

  public void setFeiertag(boolean feiertag) {
    this.feiertag = feiertag;
  }

  public boolean isGleitzeittag() {
    return gleitzeittag;
  }

  public void setGleitzeittag(boolean gleitzeittag) {
    this.gleitzeittag = gleitzeittag;
  }



}
