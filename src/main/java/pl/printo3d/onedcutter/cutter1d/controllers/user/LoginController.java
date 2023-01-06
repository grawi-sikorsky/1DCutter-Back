package pl.printo3d.onedcutter.cutter1d.controllers.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.dto.UserDTO;
import pl.printo3d.onedcutter.cutter1d.dto.UserRegisterDTO;
import pl.printo3d.onedcutter.cutter1d.models.user.AuthRequest;
import pl.printo3d.onedcutter.cutter1d.models.user.AuthResponse;
import pl.printo3d.onedcutter.cutter1d.services.UserService;

@CrossOrigin(origins = { "http://localhost:4200", "http://10.0.2.2:8080", "http://localhost", "*" })
@RestController
@RequestMapping("/user")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> registerForm(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userRegisterDTO));
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<AuthResponse> authenticateRequest(@RequestBody AuthRequest aRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.doLogin(aRequest));
    }
}
