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

import pl.printo3d.onedcutter.cutter1d.dto.UserDTO;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.user.AuthRequest;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;
import pl.printo3d.onedcutter.cutter1d.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepo uRepo;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder pEncoder;

    public UserService(UserRepo uRepo) {
        this.uRepo = uRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
        return uRepo.findByUsername(arg0);
    }

    /**
     * Logowanko
     * @param aRequest
     * @return
     */
    public boolean doLogin(AuthRequest aRequest) {
        UserModel um = uRepo.findByUsername(aRequest.getUsername());

        if (um != null) {
            if (passwordEncoder().matches(aRequest.getPassword(), um.getPassword())) {
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

            if (!uRepo.existsByUsername(userModel.getUsername())) {

                userModel.setPassword(pEncoder.encode(userModel.getPassword()));
                uRepo.save(userModel);
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

    public UserModel updateUser(UserDTO userDTO) {
        logger.info("Update User..");

        UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel uModel = (UserModel) this.loadUserByUsername(ud.getUsername());

        uModel.setPhone(userDTO.getPhone());
        uModel.setWebsite(userDTO.getWebsite());
        uModel.setActiveOrderId(userDTO.getActiveOrderId());

        uRepo.save(uModel);

        return uModel;
    }

    public void saveUserEntity(UserModel um) {
        uRepo.save(um);
    }

    /**
     * TESTOWE, zwraca tylko zapisane ordery usera
     * @param user
     * @return
     */
    public List<ProjectModel> getListOfSavedProjects(UserModel user) {
        List<ProjectModel> oList;
        oList = uRepo.findByUsername(user.getUsername()).getSavedOrderModels();

        return oList;
    }



}
