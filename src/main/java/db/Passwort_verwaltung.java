package db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Passwort_verwaltung {
  Einstellungen cnf = new Einstellungen();

  public boolean pruefePasswort(String eingabePW,String pruefhash,String salt) throws NoSuchAlgorithmException {
    if (get_hash(eingabePW,salt).equals(pruefhash)) return true;
    else return false;
  }// Prüfe Passwort

  public boolean pw_richtlinen_check(String password){
    boolean zahl_enthlaten=false;
    boolean char_klein=false;
    boolean char_groß = false;
    boolean sonderzeichen = false;

    char[] chars = password.toCharArray();

    // Längen überprüfen:
    if (chars.length > cnf.pw_max_lengt || chars.length < cnf.pw_min_lenght){
      System.err.println("Länge des Passwortes zu kurz");
      return false;
    }

    // Sonderzeichen/Zahl überprüfen
    for (int i=0; i<chars.length;i++){
      if (Character.isDigit(chars[i])) zahl_enthlaten = true;
      if(Character.isUpperCase(chars[i])) char_groß = true;
      if(Character.isLowerCase(chars[i])) char_klein = true;
      if (!Character.isLetterOrDigit(chars[i])) sonderzeichen = true;
    }

    if (cnf.pw_Sonzerzeichen == true && sonderzeichen == false){
      System.err.println("Kein Sonderzeichen troz Vorgabe");
      return false;
    }
    if (cnf.pw_gk_schreibung == true && char_groß == false || char_klein==false){
      System.err.println("Keine Groß/Klein Schreibung trotz Vorgabe");
      return false;
    }
    if (cnf.pw_Zahl == true && zahl_enthlaten==false){
      System.err.println("Keine Zahl im Passwort trotz Vorgabe");
      return false;
    }

    return true;
  }

  public String get_hash(String password, String salt) {
    String hash = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-1");
      md.update(salt.getBytes());
      byte[] bytes = md.digest(password.getBytes());
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
          .substring(1));
      } // END for
      hash = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }//  END hash
    return hash;
  }//get Hash

  // Salt Genriren
  public String get_Salt() throws NoSuchAlgorithmException {
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
    byte[] salt = new byte[16];
    sr.nextBytes(salt);
    return salt.toString();
  }// get salt
}// passwort Verwaltung
