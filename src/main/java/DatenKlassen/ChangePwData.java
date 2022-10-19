package DatenKlassen;

import lombok.Data;

/**
 * Klasse für Daten bei Passwortaenderung
 * @author Noah Dambacher
 * @version 1.0
 */
@Data
public class ChangePwData {
  private String mid;
  private String pw;
}
