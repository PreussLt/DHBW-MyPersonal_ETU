import java.sql.Connection;

public class Controller {
  public static sql_statment sql = new sql_statment();

  public static void main(String[] args) {
    Connection ETU = sql_connect.etu_connect();
   sql.sql_select("uk_userklassen","*","",ETU);

  }
}
