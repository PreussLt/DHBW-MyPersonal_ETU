package db;

import java.security.NoSuchAlgorithmException;

public class Controller {
  public static sql_statment sql = new sql_statment();
  public static Passwort_verwaltung pwv = new Passwort_verwaltung();
  public static Nutzerverwaltung nzv = new Nutzerverwaltung();
  public static Einstellungen cnf = new Einstellungen();
  public static Buchung bch = new Buchung();
  public static GetBuchungsdaten bcd = new GetBuchungsdaten();
  public static void main(String[] args) throws NoSuchAlgorithmException {

    //nzv.existiertNutzer("Kyle","Henselmann");
    try {
      System.out.println(bcd.getArbeitszeit("1","2022-09-09"));
    }catch (Exception e){

    }

    System.out.println(pwv.pruefePasswort("Kyle2002_","508dfffdee2536aa75990be2b6b92aaa7d3807d0","[B@101df177"));

    //System.out.println(nzv.nutzer_anlegen("Chris","Henselmann",1604,"Kyle2002_",1,1));
    //String[] cars = {"Tester2", "Willi", "51651", "",""};
    //sql.insert(cnf.mitarbeiter,cars,con);


    //System.out.println(pwv.pruefePasswort("Test1234", pwv.get_hash("Test1234", salt), salt));

    }
  }
