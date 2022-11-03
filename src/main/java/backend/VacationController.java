package backend;

import DatenKlassen.Gleitzeittag;
import DatenKlassen.Vacation;
import db.Einstellungen;
import db.Freietage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Noah Dambacher
 * @version 1.0
 */
@RestController
@CrossOrigin (origins = Einstellungen.origins)
public class VacationController {

  /**
   * Requesthandler zum Eintragen von Urlaub
   * @param vacation Zeitdaten des Urlaubs
   * @see Vacation
   * @return boolean - hat das Eintragen des Urlaubs geklappt?
   */
  @PostMapping("/setVacation")
  public boolean setVacation(@RequestBody Vacation vacation){
    Freietage freietage = new Freietage();
    return freietage.setUrlaub(vacation.getDateBegin(), vacation.getDateEnd(), vacation.getMid());
  }

  /**
   * Requesthandler zum Eintragen eines Gleitzeittages
   * @param gleitzeittag Zeitdaten des Gleitzeittages
   * @see Gleitzeittag
   * @return boolean - Hat das Eitnragen des Gleitzeittages geklappt?
   */
  @PostMapping("/setFlexibleday")
  public String setFlexibleday(@RequestBody Gleitzeittag gleitzeittag){
    Freietage freietage = new Freietage();
    return freietage.setGleitzeittag(gleitzeittag.getDate(), gleitzeittag.getMid());
  }

  /**
   * Requesthandler zum Loeschen eines Urlaubstages/Gleitzeittages
   * @param date Datum des Urlaubstages/Gleitzeittages
   * @return boolean - Hat das Loeschen des Urlaubstages/Gleitzeittages geklappt?
   */
  @PostMapping("/deleteFreiertag")
  public boolean deleteFreiertag(@RequestBody String date){
    return Freietage.deleteFreiertag(date);
  }
}
