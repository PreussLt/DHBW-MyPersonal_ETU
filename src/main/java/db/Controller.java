package db;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

public class Controller {
  public static sql_statment sql = new sql_statment();
  public static Passwort_verwaltung pwv = new Passwort_verwaltung();
  public static Nutzerverwaltung nzv = new Nutzerverwaltung();
  public static Einstellungen cnf = new Einstellungen();

  public static void main(String[] args) throws NoSuchAlgorithmException {

    String salt = pwv.get_Salt();
    Connection con = sql_connect.intern_connect();

    System.out.println(nzv.nutzer_löschen("8"));
    //System.out.println(nzv.nutzer_anlegen("Chris","Henselmann",1604,"Kyle2002_",1,1));
    //String[] cars = {"Tester2", "Willi", "51651", "",""};
    //sql.insert(cnf.mitarbeiter,cars,con);


    //System.out.println(pwv.pruefePasswort("Test1234", pwv.get_hash("Test1234", salt), salt));

    }
  }
