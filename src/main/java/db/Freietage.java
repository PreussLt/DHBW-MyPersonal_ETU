package db;

import DatenKlassen.ArbeitstagListe;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class Freietage {
  // Andere Klassen
  private static final sql_statment sql = new sql_statment();
  private static final Connection con = sql_connect.intern_connect();
  private static final String SUNDAY = "SUNDAY";
  private static final String SATURDAY = "SATURDAY";
  private final String WEEKEND = "WEEKEND";
  private final String SUCCESS = "SUCCESS";
  private final String FAILURE = "FAILURE";
  private final String TAKEN = "TAKEN";

  public double getGleitzeitstand(ArbeitstagListe ab){
    double ausgabe = 0;
    for (int i=0; i<ab.arbeitstage.length;i++){
        ausgabe += ab.arbeitstage[i].arbeitszeit - ab.arbeitstage[i].sollArbeitszeit;
    }
    return ausgabe;
  }// GetGleitzeitstand

  public String setGleitzeittag(String tag,String mid){
    if(isDayTaken(tag, mid) || isVacationDayTaken(tag, mid)) return TAKEN;
    else {
      try {
        String[] Daten = {mid, "0", tag};
        if(!isWeekend(tag)) {
          if(sql.insert(Einstellungen.gleitzeittage, Daten, con)) return SUCCESS;
          else return FAILURE;
        }
        else return WEEKEND;
      } catch(Exception e) {
        System.err.println("!ERROR! Fehler in setGleitzeittag: " + e);
        return FAILURE;
      }
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

  public static boolean deleteFreiertag(String date){
    try {
      String bedingung = String.format("MG_TAG = '%s'",date);
      return sql.delete(Einstellungen.gleitzeittage, bedingung, con);
    }catch (Exception e){
      System.err.println("!ERROR! Fehler in deleteFreiertag: "+e);
      return false;
    }

  }

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
        if(!isWeekend(date1) && !isDayTaken(date1, mid) && !isVacationDayTaken(date1, mid)){
          setUrlaubstag(date1, mid);
        }
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

  public static boolean isWeekend(String date){
    LocalDate localDate = LocalDate.parse(date);
    String day = localDate.getDayOfWeek().toString();
    return day.equals(SATURDAY) || day.equals(SUNDAY);
  }

  public boolean isDayTaken(String day, String mid){
    Buchung buchung = new Buchung();
    return (buchung.ueberpruefeBuchungvorhanden(day, mid, con));
  }

  public boolean isVacationDayTaken(String day, String mid){
      // Überprüfen ob für den Heutigen Tag schon eine Buchung Besteht
      String sBedingung = "WHERE MG_M_ID='" + mid + "'  AND MG_TAG='" + day + "'";
      //System.out.println(sBedingung);// --> Debuggin
      String[][] tmpArr = sql.select_arr(Einstellungen.gleitzeittage, "*", sBedingung, con);
      // Überprüfen ob Buchung doppelt, mehrfach oder schon vorhanden ist
      if (tmpArr.length>1){
        System.err.println("!Error! Doppelte Buchung, Prozess wird vorgesetzt");
        return true;
      }// END IF
      else if (tmpArr.length==1){
        System.out.println("*Info* Buchung vorhanden");
        return true;
      } else return false;
  }

}// Class
