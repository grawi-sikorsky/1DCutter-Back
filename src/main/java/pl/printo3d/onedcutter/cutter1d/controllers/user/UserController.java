package pl.printo3d.onedcutter.cutter1d.controllers.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.dto.UserDTO;
import pl.printo3d.onedcutter.cutter1d.dto.UserUpdateDTO;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;
import pl.printo3d.onedcutter.cutter1d.services.UserService;

@RequestMapping("/users")
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

        logger.info("Active order: {}", userDTO.getactiveProjectId());
        return userDTO;
    }

    // TODO: change to USER DTO
    @PatchMapping
    public UserDTO updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        return new UserDTO(userService.updateUser(userUpdateDTO));
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> removeUser(@PathVariable String uuid){
        userService.removeUser(uuid);
        return ResponseEntity.noContent().build();
    }

    // @PatchMapping
    // public UserDTO updateUser(@RequestBody UserDTO userDTO){
    //     UserModel userModel = (UserModel) userService.loadUserByUsername( ((UserModel)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );
    //     userService.updateUser(userDTO)
    //     return ;
    // }
}
