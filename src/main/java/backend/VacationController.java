package backend;

import DatenKlassen.Gleitzeittag;
import DatenKlassen.Vacation;
import db.Freietage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class VacationController {

  @PostMapping("/setVacation")
  public boolean setVacation(@RequestBody Vacation vacation){
    Freietage freietage = new Freietage();
    return freietage.setUrlaub(vacation.getDateBegin(), vacation.getDateEnd(), vacation.getMid());
  }

  @PostMapping("/setFlexibleday")
  public boolean setFlexibleday(@RequestBody Gleitzeittag gleitzeittag){
    Freietage freietage = new Freietage();
    return freietage.setGleitzeittag(gleitzeittag.getDate(), gleitzeittag.getMid());
  }
}
