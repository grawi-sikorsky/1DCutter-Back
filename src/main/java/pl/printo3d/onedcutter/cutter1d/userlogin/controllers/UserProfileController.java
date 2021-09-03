package pl.printo3d.onedcutter.cutter1d.userlogin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.userlogin.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserProfileController {

  @Autowired
  private UserService uService;
  

  @RequestMapping(value="/profile", method = RequestMethod.POST)
  public boolean profileUpdate( @RequestBody UserModel userModel )
  {
    UserModel uModel = (UserModel)uService.loadUserByUsername(userModel.getUsername());

    uModel.setPhone(userModel.getPhone());
    uModel.setWebsite(userModel.getWebsite());
    uService.updateUser(uModel);

    return true;
  }  
}
