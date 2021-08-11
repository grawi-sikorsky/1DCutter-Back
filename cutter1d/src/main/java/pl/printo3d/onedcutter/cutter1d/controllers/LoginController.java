package pl.printo3d.onedcutter.cutter1d.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*")
public class LoginController {
  
  @RequestMapping(value="/login", method=RequestMethod.GET)
  public String logujemy()
  {
    System.out.println("logujo z angulara!");
    return "login";
  }

  @RequestMapping(value="/register", method = RequestMethod.GET)
  public String registerForm()
  {
    return "register";
  }
}
