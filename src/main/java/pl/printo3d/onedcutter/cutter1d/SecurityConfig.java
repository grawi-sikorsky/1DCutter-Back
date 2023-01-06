package pl.printo3d.onedcutter.cutter1d;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import pl.printo3d.onedcutter.cutter1d.services.JwtUserDetailsService;
import pl.printo3d.onedcutter.cutter1d.utility.JWTFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtUserDetailsService uService;

    @Autowired
    JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(JwtUserDetailsService uService) {
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
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200","http://10.0.2.2:8080","http://localhost"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));

        http.authorizeRequests()
            .antMatchers("/user/register", "/img/**", "/css/**").permitAll()  // refactored
            .antMatchers("/user/auth").permitAll()
            .antMatchers("/user").permitAll()
            .antMatchers("/swagger-ui/index.html").permitAll()
            
            .antMatchers("/login", "/img/**", "/css/**").permitAll()
            .antMatchers("/1dcut").permitAll()
            .antMatchers("/cut").permitAll()
            .antMatchers("/cutfree").permitAll()
            .antMatchers("/setorder").permitAll()
            .antMatchers("/result").permitAll()
            .antMatchers("/profile").permitAll()
            .antMatchers("/test").permitAll()

            .antMatchers("/v2/api-docs",
                            "/configuration/ui",
                            "/swagger-resources/**",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/swagger-ui").permitAll();
            //.anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();
        http.cors().configurationSource(request -> corsConfiguration);
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring()
        .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**");
    }
}
