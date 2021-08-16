package pl.printo3d.onedcutter.cutter1d;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import pl.printo3d.onedcutter.cutter1d.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
  
  private UserService uService;

  @Autowired
  public SecurityConfig(UserService uService) {
    this.uService = uService;
  }

  public SecurityConfig(){}

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //super.configure(auth);
    auth.userDetailsService(uService);
/*
    auth.inMemoryAuthentication()
      .withUser("kloc")
      .password(new BCryptPasswordEncoder().encode("kloc"))
      .roles("USER");
      */
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //super.configure(http);

    http.authorizeRequests()
    .antMatchers("/login","/img/**","/css/**").permitAll()
    .antMatchers("/register", "/img/**","/css/**").permitAll()
    .antMatchers("/1dcut").permitAll()
    .antMatchers("/test").permitAll()
    .antMatchers("/login2").permitAll()

    .antMatchers("/").permitAll()
    .anyRequest().authenticated();

    http.httpBasic();

    http.formLogin().permitAll()
      .loginPage("/login").permitAll()
      .and()
      .logout().permitAll()
      .deleteCookies("JSESSIONID");

    http.csrf().disable();
    http.cors();
  }
}
