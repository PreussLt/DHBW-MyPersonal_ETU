package backend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

}
