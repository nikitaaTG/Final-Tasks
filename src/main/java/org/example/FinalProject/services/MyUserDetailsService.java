package org.example.FinalProject.services;

import java.util.Collection;
import java.util.Collections;

import org.example.FinalProject.enums.RoleOnSite;
import org.example.FinalProject.models.UserEntity;
import org.example.FinalProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // FIXME: юзер можно подключить без полного имени пакета
        return new User(user.getEmail(), user.getPassword(), !user.isUserDeleted(), true, true, true,
                getAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            RoleOnSite role) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

}
