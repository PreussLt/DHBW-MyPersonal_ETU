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
//  private static final String SATURDAY = "SATURDAY";
  private final String WEEKEND = "WEEKEND";
  private final String SUCCESS = "SUCCESS";
  private final String FAILURE = "FAILURE";
  private final String TAKEN = "TAKEN";

  /**
   * Berechnen des Gleitzeitstandes eines Mitarbeiters
   * @param ab ArbeitstagListe eines Mitarbeiters
   * @see ArbeitstagListe
   * @return Gleitzeitstand als Double
   */
  public double getGleitzeitstand(ArbeitstagListe ab){
    double ausgabe = 0;
    for (int i=0; i<ab.arbeitstage.length;i++){
        ausgabe += ab.arbeitstage[i].arbeitszeit - ab.arbeitstage[i].sollArbeitszeit;
    }
    return ausgabe;
  }// getGleitzeitstand

  /**
   * Eintragen eines Gleitzeittages
   * @param tag Tag des Eintrags
   * @param mid Mitarbeiter ID
   * @return Status des Erfolgs als String
   */
  public String setGleitzeittag(String tag,String mid){
    //Wenn Tag belegt (Buchung/Freiertag)
    if(isDayTaken(tag, mid) || isVacationDayTaken(tag, mid)) return TAKEN;
    else {
      try {
        String[] Daten = {mid, "0", tag};

        //Wenn nicht am Sonntag
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
  }// setGleitzeitatag

  /**
   * Eintragen eines Urlaubstags
   * @param tag Datum des Urlaubstags
   * @param mid Mtiarbeiter ID
   * @return boolean - Hat das Eintragen des Urlaubstages geklappt?
   */
  public boolean setUrlaubstag(String tag,String mid){
    try {
      //1=Urlaub; 0 wäre Gleitzeittag
      String[] Daten = {mid,"1",tag};
      return sql.insert(Einstellungen.gleitzeittage,Daten,con);
    }catch (Exception e){
      System.err.println("!ERROR! Fehler in setUrlaubstag: "+e);
      return false;
    }
  }// setUrlaubstag

  /**
   * Loeschen eines Urlaub-/Gleitzeittags
   * @param date Datum des Urlaub-/Gleitzeittags
   * @return boolean - Hat das Loeschen des Urlaub-/Gleitzeittags geklappt?
   */
  public static boolean deleteFreiertag(String date){
    try {
      String bedingung = String.format("MG_TAG = '%s'",date);
      return sql.delete(Einstellungen.gleitzeittage, bedingung, con);
    }catch (Exception e){
      System.err.println("!ERROR! Fehler in deleteFreiertag: "+e);
      return false;
    }

  }

  /**
   * Urlaub fuer einen oder mehrere Tage eintragen
   * @param dateBegin Erstes Datum des Urlaubs
   * @param dateEnd Letztes Datum des Urlaubs
   * @param mid Mitarbeiter ID
   * @return boolean - Hat das Eintragen geklappt?
   */
  public boolean setUrlaub(String dateBegin, String dateEnd, String mid){
    try {
      //Datum rechenbar machen
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Calendar c = Calendar.getInstance();
      c.setTime(sdf.parse(dateEnd));
      //1 Tag dazu, damit letzer Tag auch eingetragen wird
      c.add(Calendar.DATE, 1);

      //Anfangsdatum rechenbar machen
      String date2 = sdf.format(c.getTime());
      c.setTime(sdf.parse(dateBegin));

      String date1 = sdf.format(c.getTime());

      //Während Anfangsdatum nicht gleich Enddatum+1
      while(!date1.equals(date2)){
        //Wenn nicht Sonntag oder belegt Urlaub eintragen
        if(!isWeekend(date1) && !isDayTaken(date1, mid) && !isVacationDayTaken(date1, mid)){
          setUrlaubstag(date1, mid);
        }
        //Ein Tag weiter
        c.add(Calendar.DATE, 1);
        date1 = sdf.format(c.getTime());
      }
      return true;
    }catch (Exception e){
      System.err.println("!ERROR! Fehler in setUrlaubstag: "+e);
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Hilfsmethode zur Pruefung, ob Tag ein Sonntag ist
   * @param date Zu ueberpruefendes Datum
   * @return boolean - Ist Tag ein Sonntag?
   */
  public static boolean isWeekend(String date){
    LocalDate localDate = LocalDate.parse(date);
    String day = localDate.getDayOfWeek().toString();
    return day.equals(SUNDAY);
  }

  /**
   * Hilfsmethode zur Pruefung, ob ein Tag bereits belegt ist
   * @param day Zu ueberpruefendes Datum
   * @param mid Mitarbeiter ID
   * @return boolean - Ist Tag belegt?
   */
  public boolean isDayTaken(String day, String mid){
    Buchung buchung = new Buchung();
    return (buchung.ueberpruefeBuchungvorhanden(day, mid, con));
  }

  /**
   * Hilfsmethode zur Pruefung, ob ein Tag bereits mit Urlaub-/Gleitzeittag belegt ist
   * @param day Zu ueberpruefendes Datum
   * @param mid Mitarbeiter ID
   * @return boolean - Ist Tag belegt?
   */
  public boolean isVacationDayTaken(String day, String mid){
      // Überprüfen ob für den Heutigen Tag schon ein Eintrag besteht
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
