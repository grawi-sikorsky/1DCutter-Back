package pl.printo3d.onedcutter.cutter1d.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
  

  @GetMapping("/login")
  public UserModel user()
  {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserModel um;
    if(principal == "anonymousUser")
    {
      System.out.println("nulex!");
      um = new UserModel("AnonymousUser", "AnonymousUser");
    }
    else
    {
      System.out.println("GET Loginpage z angulara!");
      //return new UserModel("kupa", "kupa", "kupa");
      um = (UserModel)uService.loadUserByUsername(((UserModel)principal).getUsername());
    }
    return um;
  }
  
  @RequestMapping(value="/register", method = RequestMethod.POST)
  public boolean registerForm(@RequestBody UserModel uModel)
  {
    if(uService.addUser(uModel) == true)
    {
      System.out.println("Register done..");
      return true;
    }
    else
    {
      System.out.println("Register error");
      return false;
    }
  }
}
