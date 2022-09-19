package db;

import org.junit.jupiter.api.Test;

class PasswortVerwaltungTest {
  @Test
  public void pruefePasswortTest(){
    Passwort_verwaltung passwort_verwaltung = new Passwort_verwaltung();
    String pw = "cool";
    System.out.println(passwort_verwaltung.get_hash(pw, "[B@101df177"));
  }
}
