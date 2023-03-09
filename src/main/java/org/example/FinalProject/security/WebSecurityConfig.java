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


    //Remove unnecessary code after debugging!!!!!!!!!!!!!
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/assortment/*", "/", "/about", "/products", "/user/registration", "/assortment"
//                        ).permitAll()
//                        .requestMatchers("/user/all").hasRole("CLIENT")
//                        .requestMatchers("/manager/*").hasRole("MANAGER")
//                        .anyRequest().authenticated()
//                )
        http.csrf().disable()
//                .authorizeHttpRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
                .formLogin((form) -> form
                        .loginPage("/login"));
//                        .permitAll()
//                )
//                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
