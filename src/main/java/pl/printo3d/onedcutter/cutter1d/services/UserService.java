package pl.printo3d.onedcutter.cutter1d.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.dto.UserUpdateDTO;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.user.AuthRequest;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;
import pl.printo3d.onedcutter.cutter1d.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepo userRepo;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder pEncoder;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        return userRepo.findByUsername(arg0);
    }

    /**
     * Logowanko
     * @param aRequest
     * @return
     */
    public boolean doLogin(AuthRequest aRequest) {
        UserModel userModel = userRepo.findByUsername(aRequest.getUsername());

        if (userModel != null) {
            if (passwordEncoder().matches(aRequest.getPassword(), userModel.getPassword())) {
                return true;        // login success
            } else return false;    // pass doesnt match
        } else return false;        // user doesnt exists
    }

    /**
     * Rejestruje usera
     * @param userModel
     * @return
     */
    public boolean addUser(UserModel userModel) {
        if (userModel.getUsername() != "" && userModel.getPassword() != "" && userModel.getEmail() != ""
            && userModel.getUsername() != null && userModel.getPassword() != null && userModel.getEmail() != null) {

            if (!userRepo.existsByUsername(userModel.getUsername())) {

                userModel.setPassword(pEncoder.encode(userModel.getPassword()));
                userRepo.save(userModel);
                logger.info("Dodajemy Usera..");

                return true;
            } else {
                logger.info("User exists!");
                return false;
            }
        } else {
            logger.info("Bad kredenszals!");
            return false;
        }
    }

    

    public UserModel updateUser(UserUpdateDTO userUpdateDTO) {
        UserModel userModel = (UserModel) userRepo.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() );

        if( userRepo.findByUuid(userModel.getUuid()) != null){
            userModel.setPhone(userUpdateDTO.getPhone());
            userModel.setWebsite(userUpdateDTO.getWebsite());
            userModel.setactiveProjectId(userUpdateDTO.getactiveProjectId());
            userModel.setactiveProjectModel(userModel.getsavedProjectModels().get(userModel.getactiveProjectId()));
    
            userRepo.save(userModel);
            logger.info("Update User..");
            return userModel;
        }
        throw new RuntimeException("No such user for update!");
    }

    public void saveUserEntity(UserModel userModel) {
        userRepo.save(userModel);
    }


    public void removeUser(String uuid) {
        UserModel userModel = userRepo.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

        if( (userRepo.findByUuid(uuid) != null) && userModel.getUuid().equals(uuid) ){
            userRepo.deleteByUuid(uuid);
        } else throw new RuntimeException("No uuid found, or user dont have access to delete this uuid");
    }
}
