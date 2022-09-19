package DatenKlassen;

import db.ArbeitstagPruefen;

public class Arbeitstag {
  private ArbeitstagPruefen aTag = new ArbeitstagPruefen();
  private String tag;
  private double arbeitszeit;
  private String[] Zeistempel;
  private String ersterStempel;
  private String letzterStempel;
  private boolean feiertag=false;
  private boolean gleitzeittag=false;
  private String mid;



  public Arbeitstag(String tag, double arbeitszeit, String[] zeistempel, String ersterStempel, String letzterStempel,String mid) {
    this.tag = tag;
    this.arbeitszeit = arbeitszeit;
    Zeistempel = zeistempel;
    this.ersterStempel = ersterStempel;
    this.letzterStempel = letzterStempel;
    this.mid = mid;
  }// Constructor

  private void vorgabenAnwenden(){
    // Überpüfe ob Tag Feiertag ist
    feiertag = aTag.istTagFeiertag(this.tag);
    if (feiertag) arbeitszeit = Sollarbetiszeit();



  }

  public double Sollarbetiszeit(){
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
