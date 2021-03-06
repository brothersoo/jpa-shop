package jpabook.jpashop.simpletest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

  @GetMapping("/hello")
  public String Hello(Model model) {
    Hello hello = new Hello();
    hello.setMessage("Mr. Pablo");
    model.addAttribute("data", hello.getMessage());
    return "hello";
  }
}
