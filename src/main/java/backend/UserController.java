package backend;

import db.Nutzerverwaltung;
import db.Passwort_verwaltung;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;


@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class UserController {

  @PostMapping("/userauth")
  public boolean getUsers(@RequestBody LoginData loginData) {
    Passwort_verwaltung pv = new Passwort_verwaltung();
    Nutzerverwaltung nv = new Nutzerverwaltung();
    boolean isMatching = false;
    if(nv.existiertNutzerMitPersonalnummer(loginData.getPersonalnummer())) {
      User user = nv.getUser(loginData.getPersonalnummer());
      try {
        if(pv.pruefePasswort(loginData.getPassword(), user.getPasshash(), user.getSalt())) {
          isMatching = true;
        }
      } catch(NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
      }
      //}
    }
    return isMatching;
  }

  @PostMapping("/getMid")
  public String getMid(@RequestBody String personalnummer){
    Nutzerverwaltung nv = new Nutzerverwaltung();
    User user = nv.getUser(personalnummer);
    return user.getId();
  }
}
