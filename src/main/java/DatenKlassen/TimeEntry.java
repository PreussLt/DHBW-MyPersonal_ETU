package DatenKlassen;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeEntry implements Comparable<TimeEntry>{
  private String zid;
  private String bid;
  private String timestamp;

  @Override
  public int compareTo(TimeEntry o) {
    return getTimestamp().compareTo(o.getTimestamp());
  }
}
