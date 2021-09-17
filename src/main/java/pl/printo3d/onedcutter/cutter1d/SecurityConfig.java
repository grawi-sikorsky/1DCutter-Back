package pl.printo3d.onedcutter.cutter1d;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import pl.printo3d.onedcutter.cutter1d.userlogin.services.UserService;
import pl.printo3d.onedcutter.cutter1d.userlogin.utility.JWTFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private UserService uService;

  @Autowired
  JWTFilter jwtFilter;

  @Autowired
  public SecurityConfig(UserService uService) {
    this.uService = uService;
  }

  public SecurityConfig() {
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(uService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
    corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setExposedHeaders(List.of("Authorization"));


    http.authorizeRequests().antMatchers("/login", "/img/**", "/css/**").permitAll()
        .antMatchers("/auth/login").permitAll()
        .antMatchers("/register", "/img/**", "/css/**").permitAll()
        .antMatchers("/1dcut").permitAll()
        .antMatchers("/cut").permitAll()
        .antMatchers("/cutfree").permitAll()
        .antMatchers("/setorder").permitAll()
        .antMatchers("/result").permitAll()
        .antMatchers("/profile").permitAll()
        .antMatchers("/test").permitAll()

        // .antMatchers("/").permitAll()
        .anyRequest().authenticated();

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    //http.formLogin().permitAll().loginPage("/login").permitAll().and().logout().permitAll().deleteCookies("JSESSIONID");

    http.csrf().disable();
    http.cors().configurationSource(request -> corsConfiguration);

  }
}
