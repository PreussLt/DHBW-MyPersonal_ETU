package DatenKlassen;

import lombok.Data;

/**
 * Klasse fuer Anmeldeinformationen
 * @author Noah Dambacher
 * @version 1.0
 */
@Data
public class LoginData {
  private String personalnummer;
  private String password;
}
