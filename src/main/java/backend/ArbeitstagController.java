package backend;

import DatenKlassen.Arbeitstag;
import DatenKlassen.ArbeitstagListe;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Noah Dambacher
 * @version 1.0
 */
@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class ArbeitstagController {

  /**
   * Requesthandler zum Ausgeben aller Arbeitstage eines Mitarbeiters
   * @param mid Mitarbeiterid
   * @see Arbeitstag
   * @see ArbeitstagListe
   * @return Arbeitstag[] Liste der Arbeitstage
   */
  @PostMapping ("/arbeitstage")
  public Arbeitstag[] getArbeitstagsliste(@RequestBody String mid){

    try {
      ArbeitstagListe arbeitstagListe = new ArbeitstagListe(mid);
      System.err.println("Folgende Ausgabe:"+arbeitstagListe.arbeitstage[0].getArbeitszeit());
      return arbeitstagListe.getArbeitstage();
    } catch (Exception e){
      System.err.println("!ERROR! in getArbeitstageListe: "+e);
      return null;
    }

  }

}
