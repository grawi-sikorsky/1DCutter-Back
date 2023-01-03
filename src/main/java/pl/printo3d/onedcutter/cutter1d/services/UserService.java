package pl.printo3d.onedcutter.cutter1d.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.dto.UserDTO;
import pl.printo3d.onedcutter.cutter1d.dto.UserRegisterDTO;
import pl.printo3d.onedcutter.cutter1d.dto.UserUpdateDTO;
import pl.printo3d.onedcutter.cutter1d.exceptions.user.BadCredentialsException;
import pl.printo3d.onedcutter.cutter1d.exceptions.user.UserDoesntExistsException;
import pl.printo3d.onedcutter.cutter1d.exceptions.user.UserExistsException;
import pl.printo3d.onedcutter.cutter1d.models.user.AuthRequest;
import pl.printo3d.onedcutter.cutter1d.models.user.AuthResponse;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;
import pl.printo3d.onedcutter.cutter1d.repo.UserRepo;
import pl.printo3d.onedcutter.cutter1d.utility.JWTUtil;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UserService(UserRepo userRepo, JWTUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Logowanko
     *
     * @param aRequest
     * @return
     */
    public AuthResponse doLogin(AuthRequest aRequest) {

        UserModel userModel = userRepo.findByUsername(aRequest.getUsername());

        if (userModel != null) {
            if (passwordEncoder().matches(aRequest.getPassword(), userModel.getPassword())) {
                return new AuthResponse(jwtUtil.generateToken(userModel)); // git, zwroc token
            } else
                throw new BadCredentialsException("Bad credentials error.");
        } else {
            throw new UserDoesntExistsException("User doesnt exist.");
        }
    }

    /**
     * Rejestruje usera
     * 
     * @param UserRegisterDTO
     * @return UserDTO
     */
    public UserDTO addUser(UserRegisterDTO userRegisterDTO) {
        if (!userRegisterDTO.getUsername().equals("") && !userRegisterDTO.getPassword().equals("") && !userRegisterDTO.getEmail().equals("")) {
            if (!userRepo.existsByUsername(userRegisterDTO.getUsername()) && !userRepo.existsByEmail(userRegisterDTO.getEmail())) {
                UserModel userToSave = new UserModel(userRegisterDTO);
                userToSave.setPassword(passwordEncoder().encode(userRegisterDTO.getPassword()));
                userRepo.save(userToSave);

                userToSave.setActiveProjectId(userToSave.getSavedProjectModels().get(0).getId().intValue());
                userRepo.save(userToSave);
                return new UserDTO(userToSave);
            } else {
                throw new UserExistsException("User already exists!");
            }
        } else {
            throw new BadCredentialsException("Bad credentials!");
        }
    }


    public UserDTO getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserDTO((UserModel) userRepo.findByUsername(userDetails.getUsername()));
    }


    public UserDTO updateUser(UserUpdateDTO userUpdateDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel userModel = (UserModel) userRepo.findByUsername(userDetails.getUsername());

        if (userRepo.findByUuid(userModel.getUuid()) != null) {
            userModel.setPhone(userUpdateDTO.getPhone());
            userModel.setWebsite(userUpdateDTO.getWebsite());

            if (userUpdateDTO.getactiveProjectId() != null) {
                userModel.setActiveProjectId(userUpdateDTO.getactiveProjectId());
            }

            userModel.setActiveProjectModel(userModel.getSavedProjectModels().stream()
                    .filter(e -> e.getId() == Long.valueOf(userModel.getActiveProjectId())).collect(Collectors.toList())
                    .get(0));

            userRepo.save(userModel);
            return new UserDTO(userModel);
        }
        throw new UserDoesntExistsException("Cannot update, user doesn't exist.");
    }


    public void removeUser(String uuid) {
        UserModel userModel = userRepo.findByUsername(
                ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        if ((userRepo.findByUuid(uuid) != null) && userModel.getUuid().equals(uuid)) {
            userRepo.deleteByUuid(uuid);
        } else
            throw new UserDoesntExistsException("No such user, or you don't have access to delete this user.");
    }


    public void saveUserEntity(UserModel userModel) {
        userRepo.save(userModel);
    }
}
