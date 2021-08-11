package pl.printo3d.onedcutter.cutter1d.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.repo.UserRepo;

@Service
public class UserService implements UserDetailsService
{
  private UserRepo uRepo;

  public UserService(UserRepo uRepo) {
    this.uRepo = uRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
    return uRepo.findByUsername(arg0);
  }


  
}
