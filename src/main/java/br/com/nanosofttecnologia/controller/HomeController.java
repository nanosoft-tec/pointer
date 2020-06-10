package br.com.nanosofttecnologia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String root() {
    return "redirect:/secure/home";
  }

  @GetMapping("/secure/home")
  public String home() {
    return "home";
  }

  @GetMapping("/secure/mark")
  public String mark() {
    return "mark";
  }

  @GetMapping("/secure/company")
  public String company() {
    return "company";
  }
}
