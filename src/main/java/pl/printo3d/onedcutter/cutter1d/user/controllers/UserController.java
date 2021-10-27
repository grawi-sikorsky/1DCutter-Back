package pl.printo3d.onedcutter.cutter1d.user.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.user.models.UserDTO;
import pl.printo3d.onedcutter.cutter1d.user.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.user.services.UserService;

@RequestMapping("/user")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public UserDTO getUser() {
        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = new UserDTO( (UserModel) userService.loadUserByUsername(ud.getUsername()));

        logger.info("Active order: {}", userDTO.getActiveOrderId());
        return userDTO;
    }

    // TODO: change to USER DTO
    @PostMapping
    public boolean updateUser(@RequestBody UserModel userModel) {
        UserModel uModel = (UserModel) userService.loadUserByUsername(userModel.getUsername());

        uModel.setPhone(userModel.getPhone());
        uModel.setWebsite(userModel.getWebsite());

        userService.updateUser(uModel);

        return true;
    }

    @PatchMapping
    public UserDTO updateUser(@RequestBody UserDTO userDTO){
        UserModel userModel = (UserModel) userService.loadUserByUsername( ((UserModel)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
        userService.updateUser(userDTO)
        return ;
    }
}
