package com.vamkthesis.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";
    private static final String LOGOUT_SUCCESS_URL = "/";


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserService).passwordEncoder(passwordEncoder()).and().inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("user"))
                .roles("ADMIN").authorities("ADMIN");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

//        httpSecurity.authorizeRequests()
//                .antMatchers("/v2/api-docs").hasAnyAuthority("ADMIN")
//                .and().httpBasic();

        // We don't need CSRF for this example
        httpSecurity.csrf().disable()
                .formLogin()
                .defaultSuccessUrl("/swagger-ui.html")//
                .failureUrl("/login?error=true")
                .and()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/swagger-ui.html").hasAuthority("ADMIN")
                .anyRequest().authenticated();
    }

}
