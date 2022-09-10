import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

public class Controller {
  public static sql_statment sql = new sql_statment();
  public static Passwort_verwaltung pwv = new Passwort_verwaltung();

  public static void main(String[] args) throws NoSuchAlgorithmException {

    String salt = pwv.get_Salt();
    System.out.println(pwv.prüfePasswort("Test1234", pwv.get_hash("Test1234", salt), salt));

    }
  }
