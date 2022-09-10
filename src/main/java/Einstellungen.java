public class Einstellungen {

  /*
    In dieser File können die Eisntellungen für die Datenbank, etc. vorgenommen weden


   */

  // Einstellungen für die Datenbanken
  public static String db = "jdbc:mysql://localhost:3306/myp_database"; //Datenbank
  public static String db_name = "myp_database"; // Datenbankname
  public static String db_user =""; // Standartd Datenbank Nutzer
  public static String db_pw =""; // Standard Datenbank Passwort

  // Tabellen Namen
  public static String mitarbeiter = "m_mitarbeiter"; // Mitarbeiter
  public static String mb_konto = "mk_konto"; // Mitarbeiter Konto
  public static String mb_buchung = "b_buchung"; // Buchungen
  public static String mb_zeiteintrag = "bz_zeiteintrag";
}
