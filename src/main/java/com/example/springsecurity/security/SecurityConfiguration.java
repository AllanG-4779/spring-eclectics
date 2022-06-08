package com.example.springsecurity.security;

import com.example.springsecurity.filter.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
    static final String[] WHITE_LIST_URLS = {"/home", "/api/admin/roles/add"};
    @Autowired
    private UserDetailsService customUserDetailsService;
    @Autowired
    private PasswordConfig passwordConfig;
    @Autowired
    private JwtFilter jwtFilter;


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//       auth.userDetailsService(userDetailService);
//    }

    @Override
        protected void configure(HttpSecurity http) throws Exception {
        passwordConfig = new PasswordConfig();

        logger.info("Attempting to authenticate user");
        http
                .cors().and().csrf().disable()
                .authorizeRequests().antMatchers("/generate","/auth/reg").permitAll()
                .antMatchers("/roles/add").hasAuthority("ADMIN")
                .antMatchers("/").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated().and()
                .exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());


    }
@Bean
   public  AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(customUserDetailsService);

        provider.setPasswordEncoder(passwordConfig.passwordEncoder());

        return provider;

    }
    @Bean
    @Override
 public AuthenticationManager authenticationManagerBean()throws Exception{

        return super.authenticationManagerBean();

    }
}
