package backend;

import DatenKlassen.BuchungModel;
import DatenKlassen.Entry;
import DatenKlassen.TimeEntry;
import db.Buchung;
import db.Buchungsdaten;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Noah Dambacher.
 * @version 1.0.
 */
@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class TimeEntryController {

  /**
   * Requesthandler zum Ausgeben aller Zeiteinträge eines Mitarbeiters.
   * @param mid MitarbeiterID als String.
   * @return TimeEntry[], wird anhand der MitarbeiterID aus der Datenbank ausgelesen.
   */
  @PostMapping("/entries")
  public TimeEntry[] getEntries(@RequestBody String mid){
    /*
    Buchung b = new Buchung();
    Buchungsdaten bd = new Buchungsdaten();
    ArrayList<BuchungModel> buchungen = b.getAllBuchungen(mid);
    ArrayList<TimeEntry> entries = new ArrayList<>();

    for(BuchungModel buchung : buchungen){
      entries.addAll(bd.getAllTimeentries(buchung.getBid()));
    }

    Collections.sort(entries);
    TimeEntry[] timeEntries = new TimeEntry[entries.size()];

    for(int i = 0; i < timeEntries.length; i++){
      timeEntries[i] = entries.get(i);
    }

    return timeEntries;

     */
    return null; // Hinzugefügt
  }

  /**
   * Requesthandler zum Anlegen eines neuen Zeiteintrags in der Datenbank.
   * @param entry Eingabedaten, bestehend aus MitarbeiterID, Datum und Zeit.
   * @see Entry
   * @return boolean - Hat der Eintrag geklappt?.
   */
  @PostMapping("/newEntry")
  public boolean newEntry(@RequestBody Entry entry){
    Buchung buchung = new Buchung();
    Buchungsdaten buchungsdaten = new Buchungsdaten();
    if(buchung.neueBuchung(entry.getMid(), entry.getDate())){
      return buchungsdaten.setZeitintrag(entry.getMid(), entry.getDate());
    }
    return false;
  }

}
