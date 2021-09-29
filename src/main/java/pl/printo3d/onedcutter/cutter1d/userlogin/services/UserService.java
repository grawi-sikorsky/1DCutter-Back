package pl.printo3d.onedcutter.cutter1d.userlogin.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.CutOptions;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.StockModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.models.AuthRequest;
import pl.printo3d.onedcutter.cutter1d.userlogin.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {
    private UserRepo uRepo;

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

                // 1 domyslne formatki
                OrderModel ord = new OrderModel();
                ord.setCutList(Arrays.asList(new CutModel("220", "5"), new CutModel("260", "5")));
                ord.setStockList(Arrays.asList(new StockModel("0", "1000", "6", "0"), new StockModel("1", "1000", "5", "0")));
                ord.setCutOptions(new CutOptions(false, 0d, false, false, 1000));
                ord.setProjectName("Default project");
                ord.setProjectCreated(LocalDateTime.now());
                ord.setProjectModified(LocalDateTime.now());

                OrderModel ord2 = new OrderModel();
                ord2.setCutList(Arrays.asList(new CutModel("220", "5"), new CutModel("260", "5")));
                ord2.setStockList( Arrays.asList(new StockModel("0", "1000", "6", "0"), new StockModel("1", "1000", "5", "0")));
                ord2.setCutOptions(new CutOptions(false, 0d, false, false, 1000));
                ord2.setProjectName("Default project2");
                ord2.setProjectCreated(LocalDateTime.now());
                ord2.setProjectModified(LocalDateTime.now());

                userModel.setActiveOrderModel(ord);
                userModel.setSavedOrderModels(Arrays.asList(ord, ord2));
                userModel.setActiveOrderId(0); // default
                userModel.setNumberOfSavedItems(userModel.getSavedOrderModels().size());

                userModel.setRole("VIP"); // role dynamicznie pasuje ustawiac.
                userModel.setPassword(pEncoder.encode(userModel.getPassword()));
                uRepo.save(userModel);
                System.out.println("uService Dodajemy Usera..");

                return true;
            } else {
                System.out.println("uService: User exists!");
                return false;
            }
        } else {
            System.out.println("uService: Bad kredenszals!");
            return false;
        }
    }

    public boolean updateUser(UserModel userModel) {
        System.out.println("UserService: Update User..");

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
