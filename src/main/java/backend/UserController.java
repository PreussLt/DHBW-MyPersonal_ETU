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

/**
 * @author Noah Dambacher.
 * @version 1.0
 */
@RestController
@CrossOrigin (origins = "http://localhost:4200")
public class UserController {

  /**
   * Requesthandler zum Authentifizieren der Anmeldedaten im Login-Fenster.
   * @param loginData Eingegebene Anmeldeinformationen, bestehend aus Personalnummer und Eingabe-Passwort.
   * @see LoginData
   * @return boolean, passen Anmeldedaten zum Datenbankeintrag?.
   */
  @PostMapping("/userauth")
  public boolean authUser(@RequestBody LoginData loginData) {
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

  /**
   * Requesthandler zum Authentifizieren eines Passworts bei Passwortaenderung.
   * @param cpwData MitarbeiterID + Eingegebenes Passwort.
   * @see ChangePwData
   * @return boolean, stimmen das eingegebene Passwort und das in der DB ueberein?.
   */
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

  /**
   * Requesthandler zum Auslesen der MitarbeiterID zu einer Personalnummer aus der DB.
   * @param personalnummer Personalnummer als String.
   * @return MitarbeiterID als String.
   */
  @PostMapping("/getMid")
  public String getMid(@RequestBody String personalnummer){
    Nutzerverwaltung nv = new Nutzerverwaltung();
    User user = nv.getUser(personalnummer);
    return user.getId();
  }

  /**
   * Requesthandler zum Aendern eines Passworts für einen bestimmten User.
   * @param cpw MitarbeiterID + Neues Passwort als String.
   * @see ChangePwData
   * @return boolean, hat der PW-Wechsel geklappt?
   */
  @PostMapping("/changepw")
  public boolean changePW(@RequestBody ChangePwData cpw){
    Nutzerverwaltung nutzerverwaltung = new Nutzerverwaltung();
    return nutzerverwaltung.passwort_aendern(cpw.getMid(), cpw.getPw());
  }
}
