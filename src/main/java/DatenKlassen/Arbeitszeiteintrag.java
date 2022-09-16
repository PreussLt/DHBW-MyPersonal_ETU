package DatenKlassen;

public class Arbeitszeiteintrag {
  private String tag;
  private double arbeitszeit;
  private String[] Zeistempel;
  private String ersterStempel;
  private String letzterStempel;

  public Arbeitszeiteintrag(String tag, double arbeitszeit, String[] zeistempel, String ersterStempel, String letzterStempel) {
    this.tag = tag;
    this.arbeitszeit = arbeitszeit;
    Zeistempel = zeistempel;
    this.ersterStempel = ersterStempel;
    this.letzterStempel = letzterStempel;
  }
}
