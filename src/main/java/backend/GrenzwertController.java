package backend;

import DatenKlassen.Grenzwerte;
import db.Einstellungen;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin (origins = Einstellungen.origins)
public class GrenzwertController {

  /**
   * Requesthandler zum Abrufen der aktuellen Grenzwerte einer Konfiguration
   * @param confnum Nummer/ID der Konfiguration
   * @see Grenzwerte
   * @return Alle Grenzwerte innerhalb der gewaehlten KOnfiguration
   */
  @PostMapping("/getGrenzwerte")
  public int[] getGrenzwerte(@RequestBody Integer confnum){
    Grenzwerte grenzwerte = new Grenzwerte();
    return grenzwerte.fetchGrenzwerte(confnum);
  }

  /**
   * Requesthandler zum Überschreiben der Grenzwerte in der DB
   * @param grenzwerte Neue Grenzwerte
   * @see Grenzwerte
   * @return boolean - Hat das Update geklappt?
   */
  @PostMapping("/setGrenzwerte")
  public boolean setGrenzwerte(@RequestBody Grenzwerte grenzwerte){
    return grenzwerte.updateGrenzwerte();
  }

  /**
   * Requesthandler zum Auslesen der Grenzwerte für einen Benutzer
   * @param mid Mitarbeiter ID
   * @see Grenzwerte
   * @return Grenzwerte als Grenzwerte
   */
  @PostMapping("/getGrenzwerteFor")
  public Grenzwerte getGrenzwerteFor(@RequestBody String mid){
    Grenzwerte grenzwerte = new Grenzwerte();
    return grenzwerte.getGrenzwerte(mid);
  }
}
