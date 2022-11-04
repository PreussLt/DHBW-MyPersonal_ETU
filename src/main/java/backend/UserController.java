package backend;

import DatenKlassen.*;
import db.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

/**
 * @author Noah Dambacher.
 * @version 1.0
 */
@RestController
@CrossOrigin (origins = Einstellungen.origins)
public class UserController {
  private static Connection con = new sql_connect().intern_connect();
  private static sql_statment sql = new sql_statment();
  private static int sso = new SSO().bekommeMitarbeiterID();

  /**
   * Requesthandler zum Authentifizieren der Anmeldedaten im Login-Fenster.
   * @param loginData Eingegebene Anmeldeinformationen, bestehend aus Personalnummer und Eingabe-Passwort.
   * @see LoginData
   * @return boolean, passen Anmeldedaten zum Datenbankeintrag?.
   */
  @PostMapping("/userauth")
  public boolean authUser(@RequestBody LoginData loginData) {
    new  azubiUpdate();
    Passwort_verwaltung pv = new Passwort_verwaltung();
    Nutzerverwaltung nv = new Nutzerverwaltung();
    boolean isMatching = false;
    if (nv.existiertNutzerMitPersonalnummer(loginData.getPersonalnummer(), con)) {
      User user = nv.getUser(loginData.getPersonalnummer());
      try {
        if (pv.pruefePasswort(loginData.getPassword(), user.getPasshash(), user.getSalt())) {
          isMatching = true;
        }
      } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
      }
      //}
    }
    return isMatching;
  }

  /**
   * Requesthandler zur Pruefung, ob ein Mitarbeiter durch SSO eingeloggt wurde
   * @return boolean - Mitarbeiter gefunden?
   */
  @PostMapping("/ssoauth")
  public boolean ssoauth(){
    return sso != -1;
  }

  /**
   * Requesthandler zum Pruefen, ob der SSO-Login aktiviert ist
   * @return boolean - Ist der SSO-Login aktiviert?
   */
  @PostMapping("/isSsoActive")
  public boolean isSsoActive() {
    return Einstellungen.sso_aktiviert;
  }

  /**
   * Requesthandler zum Erhalten der Mitarbeiter ID beim SSO-Login
   * @return Mitarbeiter ID als String
   */
  @PostMapping("/getSso")
  public String getSso(){
    return Integer.toString(sso);
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
      //Passwort mit Datenbankeintrag abgleichen
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
    return nutzerverwaltung.passwort_aendern(cpw.getMid(), cpw.getPw(),con);
  }

  /**
   * Requesthandler zum Anlegen eines neuen Nutzers
   * @param user Personendaten des neuen Users
   * @see CreateUser
   * @return boolean - Hat das Erstellen geklappt
   */
  @PostMapping("/createUser")
  public boolean createUser(@RequestBody CreateUser user){
    Nutzerverwaltung nutzerverwaltung = new Nutzerverwaltung();
    return nutzerverwaltung.nutzer_anlegen(user.getVorname(), user.getNachname(), user.getPersonalnummer(), user.getPasswort(), user.getArbeitsmodell(), user.getUklasse(), user.getGebDatum(), con);
  }

  /**
   * Requesthandler zum Ausgeben aller in der Datenbank enthaltenen User
   * @return User als User[]
   */
  @PostMapping("/getUsers")
  public User[] getUsers(){
    Nutzerverwaltung nutzerverwaltung = new Nutzerverwaltung();
    return nutzerverwaltung.getUsers(con);
  }

  /**
   * Requesthandler zum Registrieren eines neuen Geraets fuer den SSO
   * @param registerSso Daten zur Registrierung eines Geraets
   * @see RegisterSso
   * @return boolean - Hat das Registrieren geklappt?
   */
  @PostMapping("registerDevice")
  public boolean registerDevice(@RequestBody RegisterSso registerSso){
    Nutzerverwaltung nutzerverwaltung = new Nutzerverwaltung();
    return nutzerverwaltung.registerDevice(registerSso, con);
  }

}
