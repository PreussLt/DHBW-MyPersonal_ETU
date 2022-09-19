package db;

import java.sql.Connection;

public class ArbeitstagPruefen {
  private  sql_connect sql_conn = new sql_connect();
  private  sql_statment sql = new sql_statment();
  private  Einstellungen cnf = new Einstellungen();

  public  boolean istTagFeiertag(String tag){
    Connection con = sql_conn.extern_connect();
    if (!sql.select(cnf.feiertag,"f_tag","WHERE f_tag=\'"+tag+"\'",con)) return false;
    return true;
  }

  public boolean istTagGleitzeitag(String tag, String mid){
    Connection con = sql_conn.extern_connect();
    if (!sql.select(cnf.feiertag,"f_tag","WHERE f_tag=\'"+tag+"\'",con)) return false;
    return true;
  }

}
