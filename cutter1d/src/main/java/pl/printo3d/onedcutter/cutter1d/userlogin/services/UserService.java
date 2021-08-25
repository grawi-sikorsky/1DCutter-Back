package pl.printo3d.onedcutter.cutter1d.userlogin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.userlogin.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.userlogin.repo.UserRepo;

@Service
public class UserService implements UserDetailsService
{
  private UserRepo uRepo;

  @Bean
  public PasswordEncoder passwordEncoder() 
  {
      return new BCryptPasswordEncoder();
  }

  @Autowired
  private PasswordEncoder pEncoder;
  

  public UserService(UserRepo uRepo) {
    this.uRepo = uRepo;
  }

  public boolean addUser(UserModel userModel)
  {
    if( userModel.getUsername() != ""  &&  userModel.getPassword() != ""  &&  userModel.getEmail() != "" &&
        userModel.getUsername() != null  &&  userModel.getPassword() != null  &&  userModel.getEmail() != null )
    {
      if( !uRepo.existsByUsername(userModel.getUsername()))
      {
        //1 domyslne formatki
        userModel.setRole("VIP"); // role dynamicznie pasuje ustawiac.
        userModel.setPassword(pEncoder.encode(userModel.getPassword()));
        uRepo.save(userModel);
        System.out.println("uService Dodajemy Usera..");
  
        return true;
      }
      else
      {
        System.out.println("uService: User exists!");
        return false;
      }
    }
    else
    {
      System.out.println("uService: Bad kredenszals!");
      return false;
    }


  }

  public boolean updateUser(UserModel userModel)
  {
    System.out.println("Update User..");

    uRepo.save(userModel);

    return true;
  }

  @Override
  public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
    return uRepo.findByUsername(arg0);
  }
}
