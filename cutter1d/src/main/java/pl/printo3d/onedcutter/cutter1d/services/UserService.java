package pl.printo3d.onedcutter.cutter1d.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.models.UserModel;
import pl.printo3d.onedcutter.cutter1d.repo.UserRepo;

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

  public boolean addUser(UserModel um)
  {
    um.setPassword(pEncoder.encode(um.getPassword()));
    uRepo.save(um);
    System.out.println("uService Add user..");
    return true;
  }

  public boolean isLoggedIn()
  {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("getPrincipal: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    System.out.println("auth.getName: " + auth.getName());

    // na wypadek nulla
    if(auth == null)
    {
      return false;
    }

    return auth.isAuthenticated();
  }

  @Override
  public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
    return uRepo.findByUsername(arg0);
  }
}
