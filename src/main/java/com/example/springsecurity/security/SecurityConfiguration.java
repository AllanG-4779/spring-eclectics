package com.example.springsecurity.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);
    static final String[] WHITE_LIST_URLS = {"/home", "/api/admin/roles/add"};
    @Autowired
    private UserDetailsService customUserDetailsService;
    @Autowired
    private PasswordConfig passwordConfig;

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
                .authorizeRequests().antMatchers("/auth/reg", "/roles/add").permitAll()
                .antMatchers("/roles/add").hasAuthority("ADMIN")
                .antMatchers("/").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());


    }
@Bean
    AuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(customUserDetailsService);

        provider.setPasswordEncoder(passwordConfig.passwordEncoder());

        return provider;

    }
    @Bean
 public AuthenticationManager authenticationManager()throws Exception{

        return super.authenticationManagerBean();

    }
}
