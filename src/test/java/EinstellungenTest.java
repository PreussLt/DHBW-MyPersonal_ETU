import db.Einstellungen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class EinstellungenTest {
  @Test
  public void einstellungenTest(){
    Assertions.assertEquals(Einstellungen.db_name, "myp_database");
  }
}
