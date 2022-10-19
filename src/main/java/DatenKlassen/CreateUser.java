package DatenKlassen;

import lombok.Data;

@Data
public class CreateUser {
  private String vorname;
  private String nachname;
  private int personalnummer;
  private String passwort;
  private int arbeitsmodell;
  private int uklasse;
  private String gebDatum;
}
