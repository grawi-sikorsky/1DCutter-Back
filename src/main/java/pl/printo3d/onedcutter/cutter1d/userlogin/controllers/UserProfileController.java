package pl.printo3d.onedcutter.cutter1d.userlogin.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;


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

  @RequestMapping(value="/updateuser", method = RequestMethod.POST)
  public boolean updateUser( @RequestBody UserModel userModel )
  {
    UserModel uModel = (UserModel)uService.loadUserByUsername(userModel.getUsername());

    uModel.setActiveOrderId(userModel.getActiveOrderId());

    uService.updateUser(uModel);

    System.out.println("UpdateUser");

    return true;
  }

  @RequestMapping(value="/getuserprojects", method=RequestMethod.POST)
  public List<OrderModel> getListOfSavedProjects( @RequestParam UserModel userModel ) {
      return uService.getListOfSavedProjects(userModel);
  }
  

}