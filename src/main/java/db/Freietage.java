package db;

import DatenKlassen.ArbeitstagListe;

public class Freietage {
  public double getGleitzeitstand(ArbeitstagListe ab){
    double ausgabe = 0;
    for (int i=0; i<ab.arbeitstage.length;i++){
        ausgabe += ab.arbeitstage[i].arbeitszeit - ab.arbeitstage[i].sollArbeitszeit;
    }
    return ausgabe;
  }// GetGleitzeitstand
}// Class
