package pl.printo3d.onedcutter.cutter1d.controllers.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.dto.UserDTO;
import pl.printo3d.onedcutter.cutter1d.dto.UserUpdateDTO;
import pl.printo3d.onedcutter.cutter1d.services.UserService;

@CrossOrigin(origins = { "http://localhost:4200", "http://10.0.2.2:8080" })
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser());
    }

    @PatchMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userUpdateDTO));
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<Void> removeUser(@PathVariable String uuid) {
        userService.removeUser(uuid);
        return ResponseEntity.noContent().build();
    }
}
