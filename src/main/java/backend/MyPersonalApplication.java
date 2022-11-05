package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Noah Dambacher
 * @version 1.0
 */
@SpringBootApplication
public class MyPersonalApplication extends SpringBootServletInitializer {
  /**
   * @param args args.
   * Methode zum Starten des Java-Backends
   */
  public static void main(String[] args) {
    SpringApplication.run(MyPersonalApplication.class, args);
  }
}
