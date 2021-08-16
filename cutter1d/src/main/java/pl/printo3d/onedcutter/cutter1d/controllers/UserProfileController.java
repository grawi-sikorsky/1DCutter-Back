package pl.printo3d.onedcutter.cutter1d.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserProfileController {

  @Autowired
  private UserService uService;
  

//@RequestBody UserModel uModel
  @RequestMapping(value="/profile", method = RequestMethod.POST)
  public boolean profileUpdate( ) {
  //   @RequestParam(value = "phone", required = false) String phone,
  //   @RequestParam(value = "website", required = false) String website )
  // {
  //   Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  //   UserModel uModel = (UserModel)uService.loadUserByUsername(((UserModel)principal).getUsername());

  //   uModel.setPhone(phone);
  //   uModel.setWebsite(website);
  //   uService.updateUser(uModel);

    return true;
  }
  
}
