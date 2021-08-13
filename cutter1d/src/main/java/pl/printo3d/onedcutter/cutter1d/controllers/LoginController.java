package pl.printo3d.onedcutter.cutter1d.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.repo.UserRepo;
import pl.printo3d.onedcutter.cutter1d.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LoginController {

  @Autowired
  private UserService uService;
  private UserRepo userRepo;
  //UserModel uModel;
  
  //@RequestMapping(value="/login", method=RequestMethod.GET)

  /*
  @GetMapping("/login")
  public UserModel loginpage()
  {
    System.out.println("GET Loginpage z angulara!");
    return public Principal principal;
    return new UserModel("klocc","klocc","klocc");
  }
  */

  @GetMapping("/login")
  public UserModel user()
  {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    System.out.println("GET Loginpage z angulara!");
    //return new UserModel("kupa", "kupa", "kupa");
    UserModel um = (UserModel)uService.loadUserByUsername(((UserModel)principal).getUsername());
    return um;
  }


//  @CrossOrigin(origins = "http://localhost:4200")
  //@RequestMapping(value="/login", method=RequestMethod.POST)
  
  @PostMapping("/login")
  public String logujemy(@RequestParam UserModel u)
  {
    System.out.println("POST logujo z angulara!");
    //System.out.println(user);
    return "home";
  }
  
  @RequestMapping(value="/register", method = RequestMethod.GET)
  public String registerForm()
  {
    uService.addUser(new UserModel("kloc","kloc"));
    return "register";
  }
}
