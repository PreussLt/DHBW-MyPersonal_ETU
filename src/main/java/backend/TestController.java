package backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class TestController {

  @PostMapping("/")
  public void test(){
    ApplicationContext ctx = new AnnotationConfigApplicationContext(
      TestConfig.class);

    Test test = ctx.getBean(Test.class);
    System.out.println(test.getTestmsg());
    System.out.println("111111");
  }
}
