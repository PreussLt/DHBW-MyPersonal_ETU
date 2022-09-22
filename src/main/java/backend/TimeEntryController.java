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

@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class TimeEntryController {

  //Aufruf bei Request
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
