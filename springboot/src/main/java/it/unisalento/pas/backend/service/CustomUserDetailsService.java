package it.unisalento.pas.backend.service;

import it.unisalento.pas.backend.domain.Admin;
import it.unisalento.pas.backend.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AdminRepository adminRepository;
    UserDetails userDetails;

    /* L'email rappresenta lo username in questo contesto */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        final Admin admin = adminRepository.findByEmail(email);


        if(admin == null) {
            throw new UsernameNotFoundException(email);
        }


        userDetails = org.springframework.security.core.userdetails.User.withUsername(admin.getEmail()).password(admin.getPassword()).roles("ADMIN").build();

        return userDetails;
    }
}