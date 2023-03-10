package org.example.FinalProject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

//Settings of login & logout logic (logout redirect to homepage, not to login page)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin((form) -> {
                            try {
                                form
                                        .loginPage("/login")
                                        .and()
                                        .logout((logout) ->
                                                logout
                                                        .deleteCookies("remove")
                                                        .invalidateHttpSession(false)
                                                        .logoutUrl("/logout")
                                                        .logoutSuccessUrl("/"));
                            } catch (Exception e) {
                                System.out.println("Logout Error. Debug it.");
                            }
                        }
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
