package DatenKlassen;

public class Arbeitstag {
  private String tag;
  private double arbeitszeit;
  private String[] Zeistempel;
  private String ersterStempel;
  private String letzterStempel;

  private boolean feiertag=false;
  private boolean gleitzeittag=false;

  public Arbeitstag(String tag, double arbeitszeit, String[] zeistempel, String ersterStempel, String letzterStempel) {
    this.tag = tag;
    this.arbeitszeit = arbeitszeit;
    Zeistempel = zeistempel;
    this.ersterStempel = ersterStempel;
    this.letzterStempel = letzterStempel;
  }
}
