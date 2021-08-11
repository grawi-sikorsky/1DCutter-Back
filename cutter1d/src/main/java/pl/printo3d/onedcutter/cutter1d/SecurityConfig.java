package pl.printo3d.onedcutter.cutter1d;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import pl.printo3d.onedcutter.cutter1d.services.UserService;

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
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //super.configure(http);

    http.authorizeRequests()
    .antMatchers("/login","/img/**","/css/**").permitAll()
    .antMatchers("/register", "/img/**","/css/**").permitAll()
    .antMatchers("/1dcut").permitAll()
    .antMatchers("/test").permitAll()
    .antMatchers("/").permitAll()
    .anyRequest().hasAuthority("KIEP");


    http.formLogin().permitAll()
      .loginPage("/login").permitAll()
      .defaultSuccessUrl("/", true)
      .and()
      .logout().permitAll()
      .logoutSuccessUrl("/")
      .deleteCookies("JSESSIONID");

    http.csrf().disable();
    http.cors();
  }

}
