package backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
  @Bean
  public Test test(){
    return new Test();
  }
}
