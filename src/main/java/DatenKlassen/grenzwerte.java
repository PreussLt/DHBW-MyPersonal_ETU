package DatenKlassen;

import db.Einstellungen;
import db.sql_connect;
import db.sql_statment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grenzwerte {

  private String confnum;
  private String maxday;
  private String maxweek;
  private String yellow;
  private String red;

  private db.sql_statment sql_statment = new sql_statment();
  private static Connection con = sql_connect.extern_connect();

  public int[] fetchGrenzwerte(int confnum){
    String bedingung = String.format("WHERE G_ID = %d", confnum);
    String[][] grenzwerte = sql_statment.select_arr(Einstellungen.mb_grenzwerte, "*", bedingung, con);
    int[] intGrenzwerte = new int[grenzwerte[0].length];
    for(int i = 0; i<intGrenzwerte.length; i++){
      intGrenzwerte[i] = Integer.parseInt(grenzwerte[0][i]);
    }
    return intGrenzwerte;
  }

  public boolean updateGrenzwerte(){
    String[] ziele = {"G_Tag", "G_Woche", "G_Gleitzeit_Gelb", "G_Gleitzeit_Rot"};
    String[] werte = {maxday, maxweek, yellow, red};
    String bedingung = String.format("WHERE G_ID = %s", confnum);
    return sql_statment.update(Einstellungen.mb_grenzwerte, ziele, werte, bedingung, con);
  }
}
