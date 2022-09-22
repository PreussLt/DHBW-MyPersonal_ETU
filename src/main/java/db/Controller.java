package db;

import DatenKlassen.Arbeitstag;

import java.security.NoSuchAlgorithmException;

public class Controller {
  public static sql_statment sql = new sql_statment();
  public static Passwort_verwaltung pwv = new Passwort_verwaltung();
  public static Nutzerverwaltung nzv = new Nutzerverwaltung();
  public static Einstellungen cnf = new Einstellungen();
  public static Buchung bch = new Buchung();
  public static Buchungsdaten bcd = new Buchungsdaten();
  public  static void main(String[] args) throws NoSuchAlgorithmException {

    //nzv.existiertNutzer("Kyle","Henselmann");
    try {
      //System.out.println(bcd.getArbeitszeit("6","2022-09-18"));
      //Arbeitstag t1 = bcd.getArbeitszeiteintrag("6","2022-09-18");
      //System.out.println(bcd.berechneArbeitszeit("6","2022-09-18"));
      System.out.println(bcd.getDatumVonTimestamp("2022-09-09 22:00:00"));
    }catch (Exception e){
      System.err.println(e);
    }

    //System.out.println(bcd.setZeitintrag("9"));
    //System.out.println(bcd.getArbeitszeit("9"));

    System.out.println(nzv.nutzer_anlegen("Chris","Henselmann",1604,"Kyle2002_",1,1));
    //String[] cars = {"Tester2", "Willi", "51651", "",""};
    //sql.insert(cnf.mitarbeiter,cars,con);


    //System.out.println(pwv.pruefePasswort("Test1234", pwv.get_hash("Test1234", salt), salt));

    }

  }
