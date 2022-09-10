import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Passwort_verwaltung {

  public boolean prüfePasswort(String eingabePW,String prüfhash,String salt) throws NoSuchAlgorithmException {
    if (get_hash(eingabePW,salt).equals(prüfhash)) return true;
    else return false;
  }// Prüfe Passwort


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
