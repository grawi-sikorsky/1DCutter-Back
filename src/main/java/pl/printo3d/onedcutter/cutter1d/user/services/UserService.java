package pl.printo3d.onedcutter.cutter1d.user.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.user.models.AuthRequest;
import pl.printo3d.onedcutter.cutter1d.user.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.user.repo.UserRepo;

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
     * Rejestruje usera z zestawem domyslnych wartosci -> TODO: mozna rozdzielic tworzenie defaultowych wartosci od samego addUser dla czytelnosci
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

    public boolean updateUser(UserModel userModel) {
        logger.info("Update User..");

        uRepo.save(userModel);

        return true;
    }

    /**
     * TESTOWE, zwraca tylko zapisane ordery usera
     * @param user
     * @return
     */
    public List<OrderModel> getListOfSavedProjects(UserModel user) {
        List<OrderModel> oList;
        oList = uRepo.findByUsername(user.getUsername()).getSavedOrderModels();

        return oList;
    }

}
