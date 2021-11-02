package pl.printo3d.onedcutter.cutter1d.controllers.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.printo3d.onedcutter.cutter1d.models.user.AuthRequest;
import pl.printo3d.onedcutter.cutter1d.models.user.AuthResponse;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;
import pl.printo3d.onedcutter.cutter1d.services.UserService;
import pl.printo3d.onedcutter.cutter1d.utility.JWTUtil;

@CrossOrigin(origins = {"http://localhost:4200","http://10.0.2.2:8080", "https://onedcutterfront.herokuapp.com"})
@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;
    private final JWTUtil jwtUtil;

    public LoginController(UserService userService, JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /**
     * OLD, BEFORE JWT TOKEN, NOT USED?
     * @return
     */
    @GetMapping("/login")
    public UserModel user() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel um;
        if (principal == "anonymousUser") {
            logger.info("nulex!");
            um = new UserModel("AnonymousUser", "AnonymousUser");
        } else {
            logger.info("GET Loginpage z angulara!");
            um = (UserModel) userService.loadUserByUsername(((UserModel) principal).getUsername());
        }
        return um;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public boolean registerForm(@RequestBody UserModel uModel) {
        if (userService.addUser(uModel) == true) {
            logger.info("Register done..");
            return true;
        } else {
            logger.info("Register error");
            return false;
        }
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public AuthResponse authenticateRequest(@RequestBody AuthRequest aRequest) {

        if (userService.doLogin(aRequest)) {
            UserDetails ud = userService.loadUserByUsername(aRequest.getUsername());

            return new AuthResponse(jwtUtil.generateToken(ud)); // git, zwroc token
        } else {
            return new AuthResponse();                          // nie git, zwróć nicość!
        }
    }
}
