package db;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class azubiUpdate {
  private static Connection con = sql_connect.intern_connect();
  private static sql_statment sql = new sql_statment();
  public azubiUpdate(){
    try {
      String[][] azubi = sql.select_arr(Einstellungen.mitarbeiter+" m ,"+Einstellungen.mb_konto+" k ,"+Einstellungen.mb_arbeitsmodell+" a","m.M_ID, m.M_Geburtstag","WHERE a.A_ID = k.MK_A_ID AND k.MK_M_ID = m.M_ID AND a.A_ID=\'"+Einstellungen.u18_arbeitsmodel+"\'",con);
      for (int i=0; i<azubi.length;i++){
        if (getDifTime(stringToTS(azubi[i][1]))>=18){
          String[] ziele = {"MK_A_ID"};
          String[] daten = {Integer.toString(Einstellungen.ü18_arbeitsmodel)};
          sql.update(Einstellungen.mb_konto,ziele,daten,"WHERE MK_M_ID=\'"+azubi[i][0]+"\'",con);
        }
      }
    }catch (Exception e){
      System.out.println("!ERROR! Fehler in AzubiUpdate: "+e);
    }

  }



  private Timestamp stringToTS(String s){
    return Timestamp.valueOf(s);
  }
  private double getDifTime(Timestamp d1) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime dT1= d1.toLocalDateTime();
    LocalDateTime jetzt = LocalDateTime.now();
    long diffInMinutes = java.time.Duration.between(dT1, jetzt).toMinutes();
    int stunden = (int)diffInMinutes/60;
    double ausgabe = (diffInMinutes-stunden*60)*100/60.0;
    ausgabe = ausgabe/100 + (double) stunden;
    //System.out.println(ausgabe); Debugg
    return ausgabe;
  }//


}
