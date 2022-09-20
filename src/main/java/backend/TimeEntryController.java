package backend;

import db.Buchung;
import db.Buchungsdaten;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class TimeEntryController {

  //Aufruf bei Request
  @GetMapping("/entries")
  public List<TimeEntry> getEntries(){
    List<TimeEntry> tes = new ArrayList<>();
    for(int i = 0; i<5; i++){
      TimeEntry t = new TimeEntry(String.format("Id %s", i), String.format("Msg %s", i));
      tes.add(t);
    }
    return tes;
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
