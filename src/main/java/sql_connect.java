import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class sql_connect {
  // Standard Daten:
  private static String db = "jdbc:mysql://localhost:3306/myp_database"; //Datenbank
  private static String db_user =""; // Standartd Datenbank Nutzer
  private static String db_pw =""; // Standard Datenbank Passwort



  // ETU Zugriff Datenbank
  public static Connection etu_connect(){
    db_user = "myp_db_admin";
    db_pw= "DHBW1234";
    //Baut Verbindung zur Datenbank auf und überprüft die Verbindung

    try{
      Class.forName("com.mysql.jdbc.Driver");
      Connection con= DriverManager.getConnection(
        db,db_user,db_pw);
      Statement stmt=con.createStatement();
      ResultSet rs=stmt.executeQuery("show databases;");
      return con;

      //System.out.println("Connected"); --< Debug Gründe
    }//Try

    catch(Exception e){
      System.err.println("Fehler: "+e);
      return null;
    }//Catch

  } // End Connection ETU

}
