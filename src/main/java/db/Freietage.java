package db;

import DatenKlassen.ArbeitstagListe;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Freietage {
  // Andere Klassen
  private static final sql_statment sql = new sql_statment();
  private static final Connection con = sql_connect.intern_connect();

  public double getGleitzeitstand(ArbeitstagListe ab){
    double ausgabe = 0;
    for (int i=0; i<ab.arbeitstage.length;i++){
        ausgabe += ab.arbeitstage[i].arbeitszeit - ab.arbeitstage[i].sollArbeitszeit;
    }
    return ausgabe;
  }// GetGleitzeitstand

  public boolean setGleitzeittag(String tag,String mid){
    try {
      String[] Daten = {mid,"0",tag};
      return sql.insert(Einstellungen.gleitzeittage,Daten,con);
    }catch (Exception e){
      System.err.println("!ERROR! Fehler in setGleitzeittag: "+e);
      return false;
    }
  }// setGleitzeitata

  public boolean setUrlaubstag(String tag,String mid){
    try {
      String[] Daten = {mid,"1",tag};
      return sql.insert(Einstellungen.gleitzeittage,Daten,con);
    }catch (Exception e){
      System.err.println("!ERROR! Fehler in setUrlaubstag: "+e);
      return false;
    }
  }// setGleitzeitata

  public boolean setUrlaub(String dateBegin, String dateEnd, String mid){
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Calendar c = Calendar.getInstance();
      c.setTime(sdf.parse(dateEnd));
      c.add(Calendar.DATE, 1);  // number of days to add
      String date2 = sdf.format(c.getTime());
      c.setTime(sdf.parse(dateBegin));
      String date1 = sdf.format(c.getTime());
      while(!date1.equals(date2)){
        setUrlaubstag(date1, mid);
        c.add(Calendar.DATE, 1);  // number of days to add
        date1 = sdf.format(c.getTime());
      }
      return true;
    }catch (Exception e){
      System.err.println("!ERROR! Fehler in setUrlaubstag: "+e);
      e.printStackTrace();
      return false;
    }
  }

}// Class
