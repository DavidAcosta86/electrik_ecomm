package com.electrik.electrik_ecomm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurity {

    private final UserDetailsService userService;

    public WebSecurity(UserDetailsService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/", "/login", "/register", "/index", "/loginprocess",
                                "/userregister")
                        .permitAll()
                        .requestMatchers("/article/delete/**", "/article/modify/**", "/article/create/**")
                        .hasRole("ADMIN")
                        .requestMatchers("factory/registration/**", "factory/register/**", "factory/modify/**",
                                "factory/delete/**")
                        .hasRole("ADMIN")
                        .requestMatchers("user/edit/**", "user/update/**", "user/list/**",
                                "user/delete/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/factory/delete/**", "/factory/delete", "/article/**")
                        .authenticated()

                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loginprocess")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/start", true)
                        .failureUrl("/login?error=true")
                        .permitAll())
                .userDetailsService(userService)
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll());
        return http.build();
    }

    @Configuration
    public class ThymeleafConfig {
        @Bean
        public SpringSecurityDialect securityDialect() {
            return new SpringSecurityDialect();
        }
    }
}
