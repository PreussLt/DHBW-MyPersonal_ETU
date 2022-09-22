package DatenKlassen;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Noah Dambacher
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class BuchungModel {
  private String bid;
  private String mid;
  private String day;
  private String hours;
}
