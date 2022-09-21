package backend;

import DatenKlassen.ChangePwData;
import DatenKlassen.LoginData;
import DatenKlassen.User;
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

  @PostMapping("/pwauth")
  public boolean isMatching(@RequestBody ChangePwData cpwData){
    Nutzerverwaltung nutzerverwaltung = new Nutzerverwaltung();
    Passwort_verwaltung passwortVerwaltung = new Passwort_verwaltung();

    User user = nutzerverwaltung.getUserMid(cpwData.getMid());

    try {
      return passwortVerwaltung.pruefePasswort(cpwData.getPw(), user.getPasshash(), user.getSalt());
    } catch(NoSuchAlgorithmException e) {
      e.printStackTrace();
      return false;
    }

  }

  @PostMapping("/getMid")
  public String getMid(@RequestBody String personalnummer){
    Nutzerverwaltung nv = new Nutzerverwaltung();
    User user = nv.getUser(personalnummer);
    return user.getId();
  }

  @PostMapping("/changepw")
  public boolean changePW(@RequestBody ChangePwData cpw){
    Nutzerverwaltung nutzerverwaltung = new Nutzerverwaltung();
    return nutzerverwaltung.passwort_aendern(cpw.getMid(), cpw.getPw());
  }
}
