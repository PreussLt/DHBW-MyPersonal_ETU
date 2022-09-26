package DatenKlassen;

import db.ArbeitstagPruefen;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Arbeitstag {
  private ArbeitstagPruefen aTag = new ArbeitstagPruefen();
  public String ersterStempel;
  public String letzterStempel;
  public String mid;

  // Public Boolens
  public String tag;
  public double arbeitszeit;
  public String[][] zeitstempel;



  // Boolens
  public boolean feiertag = false;
  public boolean gleitzeittag = false;
  public boolean arbeitszeitenEingehalten;
  public boolean pausenEingehalten;
  public boolean maxArbeitszeitEingehalten;



  public Arbeitstag(String tag, double arbeitszeit, String[][] zeitstempel, String ersterStempel, String letzterStempel, String mid) {


    this.tag = tag;
    this.arbeitszeit = arbeitszeit;
    this.zeitstempel = zeitstempel;
    this.ersterStempel = ersterStempel;
    this.letzterStempel = letzterStempel;
    this.mid = mid;
    if (this.zeitstempel != null){
      vorgabenAnwenden();
    } else {
      // Überpüfe ob Tag Feiertag oder Gleitzeittag ist
      feiertag = aTag.istTagFeiertag(this.tag);
      gleitzeittag = aTag.istTagGleitzeitag(this.tag, mid);
      if (feiertag || gleitzeittag) this.arbeitszeit = Sollarbetiszeit();
    }

  }// Constructor

  public void vorgabenAnwenden() {
    arbeitszeitenEingehalten = ZeitGrenzen();
    pausenEingehalten = sindPausenEingehalten();
    maxArbeitszeitEingehalten = istMaxArbeitszeitEingehalten();
    System.out.println(arbeitszeitenEingehalten);

  }


  private boolean sindPausenEingehalten() {
    return true;
  }

  private boolean istMaxArbeitszeitEingehalten() {
    double maxArbeitszeit = 10; // TODO: 19.09.2022 Aus datenbank ziehen
    if (arbeitszeit > maxArbeitszeit) {
      arbeitszeit = maxArbeitszeit;
      return false;
    }
    return true;
  }

  private boolean ZeitGrenzen() {
    String Min = tag + " 06:00:00"; // TODO: 19.09.2022 Aus datenbank ziehen
    String Max = tag + " 22:00:00"; // ""

    Timestamp tMin = Timestamp.valueOf(Min);
    Timestamp tMax = Timestamp.valueOf(Max);

    if (aTag.sindZeiteneingehalten(getZeitstempel(), tMin, tMax) == 0) return true;

    this.arbeitszeit = arbeitszeit - aTag.sindZeiteneingehalten(getZeitstempel(), tMin, tMax);
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

  public String[][] getZeistempel() {
    return this.zeitstempel;
  }

  public void setZeistempel(String[][] zeistempel) {
    this.zeitstempel = zeistempel;
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
