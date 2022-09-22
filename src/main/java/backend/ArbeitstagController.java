package backend;

import DatenKlassen.Arbeitstag;
import DatenKlassen.ArbeitstagListe;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class ArbeitstagController {

  @PostMapping ("/arbeitstage")
  public Arbeitstag[] getArbeitstagsliste(@RequestBody String mid){
    ArbeitstagListe l = new ArbeitstagListe(mid);
    return l.getArbeitstage();
  }

}
