package DatenKlassen;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuchungModel {
  private String bid;
  private String mid;
  private String day;
  private String hours;
}
