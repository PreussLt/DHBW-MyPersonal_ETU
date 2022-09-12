package db;

public class Einstellungen {

  /*
    In dieser File können die Eisntellungen für die Datenbank, etc. vorgenommen weden


   */

  // db.Einstellungen für die Datenbanken
  public static String db = "jdbc:mysql://localhost:3306/myp_database"; //Datenbank
  public static String db_name = "myp_database"; // Datenbankname
  public static String db_user =""; // Standartd Datenbank Nutzer
  public static String db_pw =""; // Standard Datenbank Passwort
  // ETU Nutzer
  public static String db_user_etu ="myp_db_admin";
  public static String db_pw_etu="DHBW1234";
  //Interner DB-Zugriff
  public static String db_user_intern ="myp_db_admin";
  public static String db_pw_intern="DHBW1234";
  // Externe DB-Zugriff
  public static String db_user_extern ="myp_db_admin";
  public static String db_pw_extern="DHBW1234";

  // Tabellen Namen
  public static String mitarbeiter = "m_mitarbeiter"; // Mitarbeiter
  public static String mb_konto = "mk_konto"; // Mitarbeiter Konto
  public static String mb_buchung = "b_buchung"; // Buchungen
  public static String mb_zeiteintrag = "bz_zeitsteintrag";
  public static String mb_arbeitsmodell ="a_arbeitsmodelle";

  //  Passwort sicherheits einstellungen
  public static boolean pw_gk_schreibung = true; // Mindest eins Char muss groß / klein sein
  public static boolean pw_Zahl = true; // Mindestens eine Zahl muss dabei sein
  public static boolean pw_Sonzerzeichen = true; // Mindesten ein Sonderzeichen muss vorhanden sein
  public static int pw_min_lenght = 6; // Mindestlänge der Zahlen
  public static int pw_max_lengt=16;
}
