package DatenKlassen;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Klasse für neue Einträge
 * @author Noah Dambacher
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Entry {
  private String mid;
  private String date;
  private String time;
}
