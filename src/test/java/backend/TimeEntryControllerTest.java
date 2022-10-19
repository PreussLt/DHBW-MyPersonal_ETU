package backend;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class TimeEntryControllerTest {

  @Test
  void isWeekend() {
    String date = "2022-10-05";
    LocalDate localDate = LocalDate.parse(date);
    System.out.println(localDate.getDayOfWeek().toString());
  }
}
