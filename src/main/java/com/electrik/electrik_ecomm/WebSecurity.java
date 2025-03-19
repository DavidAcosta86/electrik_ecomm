package com.electrik.electrik_ecomm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.electrik.electrik_ecomm.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor injection for better practices
    public WebSecurity(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Define password encoder as a bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configure global authentication with injected BCryptPasswordEncoder
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder); // Use the injected password encoder
    }
}