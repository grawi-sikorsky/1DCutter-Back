package pl.printo3d.onedcutter.cutter1d.userlogin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.models.AuthRequest;
import pl.printo3d.onedcutter.cutter1d.userlogin.models.AuthResponse;
import pl.printo3d.onedcutter.cutter1d.userlogin.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.services.UserService;
import pl.printo3d.onedcutter.cutter1d.userlogin.utility.JWTUtil;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LoginController {

  @Autowired
  private UserService uService;

  @Autowired
  private JWTUtil jwtUtil;

  @GetMapping("/login")
  public UserModel user() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserModel um;
    if (principal == "anonymousUser") {
      System.out.println("nulex!");
      um = new UserModel("AnonymousUser", "AnonymousUser");
    } else {
      System.out.println("GET Loginpage z angulara!");
      um = (UserModel) uService.loadUserByUsername(((UserModel) principal).getUsername());
    }
    return um;
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public boolean registerForm(@RequestBody UserModel uModel) {
    if (uService.addUser(uModel) == true) {
      System.out.println("Register done..");
      return true;
    } else {
      System.out.println("Register error");
      return false;
    }
  }

  @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
  public AuthResponse authenticateRequest(@RequestBody AuthRequest aRequest) {

    if (uService.doLogin(aRequest)) {
      UserDetails ud = uService.loadUserByUsername(aRequest.getUsername());
      
      return new AuthResponse(jwtUtil.generateToken(ud));
    } 
    else {
      return new AuthResponse();
    }
  }

  // endpoint przez ktory przechodzi user po zalogowaniu w celu pobrania danych
  @GetMapping("/getuserdata")
  public UserModel getuserdata() {
    UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    UserModel um;
    um = (UserModel) uService.loadUserByUsername(ud.getUsername());
    
    System.out.println(um.getUserSlots().getActiveOrderId());
    OrderModel om = um.getUserSlots().getOrderModel();
    um.setOrderModel(om);
    return um;
  }

  @GetMapping("/")
  public String hometest() {
    return "jest gitarka!";
  }

}
