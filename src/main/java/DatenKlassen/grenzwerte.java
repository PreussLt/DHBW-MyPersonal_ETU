package DatenKlassen;

import db.Einstellungen;
import db.sql_connect;
import db.sql_statment;

import java.sql.Connection;

public class grenzwerte {
  private static Connection con = sql_connect.intern_connect();
  private static sql_statment sql = new sql_statment();

  public String mid;
  public int gGelb;
  public int gRot;



  public grenzwerte(String mid){
    this.mid = mid;
    try {
      String[] grenzwerte = sql.select_arr(Einstellungen.mb_konto+" k, "+Einstellungen.mb_arbeitsmodell+" a, "+Einstellungen.mb_grenzwerte +" g","G_Gleitzeit_Gelb, G_Gleitzeit_Rot ","WHERE g.G_ID = a.A_G_ID AND a.A_ID = k.MK_A_ID AND k.MK_M_ID=\'"+mid+"\'",con)[0];
      this.gGelb = Integer.parseInt(grenzwerte[0]);
      this.gRot = Integer.parseInt(grenzwerte[1]);

    }catch (Exception e){
      System.err.println("!ERROR!: Fehler in der Grenzwerte: "+e);
    }
  }
}
