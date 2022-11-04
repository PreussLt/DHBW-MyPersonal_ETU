package DatenKlassen;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Klasse fuer ausgelesene Eintraege
 * @author Noah Dambacher
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class TimeEntry implements Comparable<TimeEntry>{
  private String zid;
  private String bid;
  private String timestamp;

  /**
   * Vergleich zum Sortieren von Listen nach Timestamp
   * @param o Vergleichsobjekt
   * @return Vergleichsfaktor als int
   */
  @Override
  public int compareTo(TimeEntry o) {
    return getTimestamp().compareTo(o.getTimestamp());
  }
}
