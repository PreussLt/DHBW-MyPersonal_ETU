package backend;

import DatenKlassen.Arbeitstag;
import DatenKlassen.ArbeitstagListe;
import db.Nutzerverwaltung;
import db.sql_connect;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.extra.YearWeek;

import java.sql.Connection;
import java.time.Clock;
import java.time.LocalDate;

/**
 * @author Noah Dambacher
 * @version 1.0
 */
@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class ArbeitstagController {
  private static final Connection con = sql_connect.intern_connect();

  /**
   * Requesthandler zum Ausgeben aller Arbeitstage eines Mitarbeiters
   * @param mid Mitarbeiterid
   * @see Arbeitstag
   * @see ArbeitstagListe
   * @return Arbeitstag[] Liste der Arbeitstage
   */
  @PostMapping ("/arbeitstage")
  public Arbeitstag[] getArbeitstagsliste(@RequestBody String mid){
    ArbeitstagListe arbeitstagListe = new ArbeitstagListe(mid);
    return arbeitstagListe.getArbeitstage();
  }

  @PostMapping("/sollarbeitszeit")
  public double getSollarbeitszeit(@RequestBody String mid){
    Nutzerverwaltung nv = new Nutzerverwaltung();
    return nv.getSollarbeitszeit(mid, con);
  }

  @PostMapping("/gleitzeit")
  public double getGleitzeit(@RequestBody String mid){
    return new ArbeitstagListe(mid).getGleitzeitstand();
  }

  @PostMapping("/getCW")
  public String getCW() {
    LocalDate localDate = LocalDate.now(Clock.systemDefaultZone());
    return YearWeek.from(localDate).toString();
  }
}
