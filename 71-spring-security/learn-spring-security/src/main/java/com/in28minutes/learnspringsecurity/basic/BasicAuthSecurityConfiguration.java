package com.in28minutes.learnspringsecurity.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuthSecurityConfiguration {

    enum Role {
        ADMIN,
        USER
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> {
                    auth.anyRequest().authenticated();
                });
        http.sessionManagement(
                session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

//        http.formLogin();
        http.httpBasic(Customizer.withDefaults());
        http.csrf().disable();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("omer")
                .password("{noop}123")
                .roles(Role.USER.name())
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{noop}123")
                .roles(Role.ADMIN.name())
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }


}
