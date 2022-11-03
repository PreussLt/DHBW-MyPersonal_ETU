package DatenKlassen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import db.Einstellungen;
import db.sql_connect;
import db.sql_statment;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Grenzwerte {

  @NonNull
  private String confnum;
  @NonNull
  private String maxday;
  @NonNull
  private String maxweek;
  @NonNull
  private String yellow;
  @NonNull
  private String red;
  @JsonIgnore
  private db.sql_statment sql_statment = new sql_statment();
  @JsonIgnore
  private static Connection con = sql_connect.extern_connect();

  /**
   * Auslesen der Grenzwerte aus der Datenbank
   * @param confnum Nummer der Grenzwertkonfiguration
   * @return Grenzwerte gruen, gelb, rot der Konfiguration als int[]
   */
  public int[] fetchGrenzwerte(int confnum){
    String bedingung = String.format("WHERE G_ID = %d", confnum);
    String[][] grenzwerte = sql_statment.select_arr(Einstellungen.mb_grenzwerte, "*", bedingung, con);

    int[] intGrenzwerte = new int[grenzwerte[0].length];

    for(int i = 0; i<intGrenzwerte.length; i++){
      intGrenzwerte[i] = Integer.parseInt(grenzwerte[0][i]);
    }
    return intGrenzwerte;
  }

  /**
   * Auslesen der Grenzwerte eines bestimmten Mitarbeiters
   * @param mid Mitarbeiter ID
   * @see Grenzwerte
   * @return Grenzwerte des Gleitzeitstandes des Mitarbeiters
   */
  public Grenzwerte getGrenzwerte(String mid){
    int arbeitsmodell = getArbeitsmodell(mid);
    int grenzwertID = getGrenzwertId(arbeitsmodell);

    String bedingung = String.format("WHERE G_ID = %s", grenzwertID);
    String[][] grenzwerte = sql_statment.select_arr(Einstellungen.mb_grenzwerte, "*", bedingung, con);

    return new Grenzwerte(grenzwerte[0][0],grenzwerte[0][1],grenzwerte[0][2],grenzwerte[0][3],grenzwerte[0][4]);
  }

  /**
   * Auslesen des Arbeitsmodells eines Mitarbeiters
   * @param mid Mitarbeiter ID
   * @return Arbeitsmodell ID als int
   */
  public int getArbeitsmodell(String mid){
    String bedingung = String.format("WHERE MK_M_ID = %s", mid);
    String[][] arbeitsmodell = sql_statment.select_arr(Einstellungen.mb_konto, "MK_A_ID",  bedingung, con);
    return Integer.parseInt(arbeitsmodell[0][0]);
  }

  /**
   * Auslesen der Grenzwert ID zu einem Arbeitsmodell
   * @param arbeitsmodell Arbeitsmodell ID
   * @return Grenzwert ID als int
   */
  private int getGrenzwertId(int arbeitsmodell){
    String bedingung = String.format("WHERE A_ID = %d", arbeitsmodell);
    String[][] gleitzeit = sql_statment.select_arr(Einstellungen.mb_arbeitsmodell, "A_G_ID",  bedingung, con);
    return Integer.parseInt(gleitzeit[0][0]);
  }

  /**
   * Grenzwerte anhand der Variablen des Objektes ueberschreiben
   * @return boolean - Hat das Ueberschreiben der Grenzwerte geklappt?
   */
  public boolean updateGrenzwerte(){
    String[] ziele = {"G_Tag", "G_Woche", "G_Gleitzeit_Gelb", "G_Gleitzeit_Rot"};
    String[] werte = {maxday, maxweek, yellow, red};
    String bedingung = String.format("WHERE G_ID = %s", confnum);
    return sql_statment.update(Einstellungen.mb_grenzwerte, ziele, werte, bedingung, con);
  }
}
