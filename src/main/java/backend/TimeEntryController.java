package backend;

import DatenKlassen.BuchungModel;
import DatenKlassen.Entry;
import DatenKlassen.NewDay;
import DatenKlassen.TimeEntry;
import db.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Noah Dambacher.
 * @version 1.0.
 */
@RestController
@CrossOrigin (origins = Einstellungen.origins)
public class TimeEntryController {
  private final static Connection con = sql_connect.intern_connect();
  private final String WEEKEND = "WEEKEND";
  private final String SUCCESS = "SUCCESS";
  private final String FAILURE = "FAILURE";
  private final String TAKEN = "TAKEN";




  /**
   * Requesthandler zum Ausgeben aller Zeiteinträge eines Mitarbeiters.
   * @param mid MitarbeiterID als String.
   * @return TimeEntry[], wird anhand der MitarbeiterID aus der Datenbank ausgelesen.
   */
  @PostMapping("/entries")
  public TimeEntry[] getEntries(@RequestBody String mid){
    Buchung b = new Buchung();
    Buchungsdaten bd = new Buchungsdaten();
    ArrayList<BuchungModel> buchungen = b.getAllBuchungen(mid, con);
    ArrayList<TimeEntry> entries = new ArrayList<>();

    for(BuchungModel buchung : buchungen){
      entries.addAll(bd.getAllTimeentries(buchung.getBid(),con));
    }

    //Nach Zeit sortieren
    Collections.sort(entries);
    TimeEntry[] timeEntries = new TimeEntry[entries.size()];

    for(int i = 0; i < timeEntries.length; i++){
      timeEntries[i] = entries.get(i);
    }
    return timeEntries;
  }

  /**
   * Requesthandler zum Anlegen eines neuen Zeiteintrags in der Datenbank.
   * @param entry Eingabedaten, bestehend aus MitarbeiterID, Datum und Zeit.
   * @see Entry
   * @return boolean - Hat der Eintrag geklappt?.
   */
  @PostMapping("/newEntry")
  public String newEntry(@RequestBody Entry entry){
    //Wenn gewählter Tag nicht an Wochenende
    if(!Freietage.isWeekend(entry.getDate())){
      Buchung buchung = new Buchung();
      Buchungsdaten buchungsdaten = new Buchungsdaten();

      //Datum und Uhrzeit in ein Format bringen
      String timestamp = String.format("%s %s:00", entry.getDate(), entry.getTime());

      //Buchung und Zeiteintrag anlegen
      if(buchung.neueBuchung(entry.getMid(), entry.getDate(),con)){
        if(buchungsdaten.setZeitintrag(entry.getMid(), entry.getDate(), timestamp)) return SUCCESS;
      }

      return FAILURE;
    }
    else return WEEKEND;
  }

  /**
   * Requesthandler zum Anlegen eines neuen Arbeitstages
   * @param newDay Daten des Arbeitstageintrags
   * @see NewDay
   * @return Erfolgsstatus des Erstellens aks String
   */
  @PostMapping("/newDay")
  public String newDay(@RequestBody NewDay newDay){
    //Wenn Tag nicht belegt
    if(isDayTaken(newDay.getDate(), newDay.getMid())){
      return TAKEN;
    }
    else {
      //Wenn nicht am Sonntag
      if(!Freietage.isWeekend(newDay.getDate())) {
        //2 Einträge anlegen
        Entry stamp1 = new Entry(newDay.getMid(), newDay.getDate(), newDay.getTimeBegin());
        Entry stamp2 = new Entry(newDay.getMid(), newDay.getDate(), newDay.getTimeEnd());

        if(newEntry(stamp1).equals(SUCCESS) && newEntry(stamp2).equals(SUCCESS)) return SUCCESS;
        else return FAILURE;
      }
      else return WEEKEND;
    }
  }

  /**
   * Requesthandler zum Erhalten eines Zeiteintrags
   * @param id ID des Zeiteintrags
   * @see TimeEntry
   * @return Zeiteintrag als Timestamp
   */
  @PostMapping("/getEntry")
  public TimeEntry getEntry(@RequestBody String id){
    Buchungsdaten bd = new Buchungsdaten();
    return bd.getEntryById(id,con);
  }

  /**
   * Requesthandler zum Ueberschreiben eines Zeiteintrags
   * @param timeEntry Der zu Ueberschreibende Zeiteinrag
   * @see TimeEntry
   * @return boolean - Hat das Ueberschreiben gelappt?
   */
  @PostMapping("/updateEntry")
  public boolean updateEntry(@RequestBody TimeEntry timeEntry){
    Buchungsdaten bd = new Buchungsdaten();
    return bd.updateEntry(timeEntry,con);
  }

  /**
   * Requesthandler zum Löschen eines Zeiteintrags
   * @param zid ID des zu löschenden Zeiteintrags
   * @return boolean - Hat das Loeschen geklappt?
   */
  @PostMapping("/deleteEntry")
  public boolean deleteEntry(@RequestBody String zid){
    Buchungsdaten bd = new Buchungsdaten();
    return bd.deleteEntry(zid,con);
  }

  /**
   * Hilfsmethode zum Ueberpruefen, ob an einem Tag bereits eine Buchung vorhanden ist
   * @param day Der zu pruefende Tag
   * @param mid Mitarbeiter ID
   * @return boolean - Ist an dem Tag eine Buchung vorhanden?
   */
  public boolean isDayTaken(String day, String mid){
    Buchung buchung = new Buchung();
    return (buchung.ueberpruefeBuchungvorhanden(day, mid, con));
  }
}
