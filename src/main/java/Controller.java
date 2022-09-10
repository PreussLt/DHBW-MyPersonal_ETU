import java.security.NoSuchAlgorithmException;

public class Controller {
  public static sql_statment sql = new sql_statment();
  public static Nutzerverwaltung nuv = new Nutzerverwaltung();
  public static Einstellungen cnf = new Einstellungen();
  public static Passwort_verwaltung pwv = new Passwort_verwaltung();

  public static void main(String[] args) throws NoSuchAlgorithmException {
    String salt = pwv.get_Salt();
    System.out.println(pwv.prüfePasswort("Test1234",pwv.get_hash("Test1234",salt),salt));

    /*
    Connection ETU = sql_connect.etu_connect();
   sql.select_arr("uk_userklassen","*","",ETU);
   System.out.println(sql.get_numColums("uk_userklassen",ETU));
  */
  }
}
