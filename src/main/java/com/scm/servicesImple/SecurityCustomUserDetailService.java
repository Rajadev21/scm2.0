package com.scm.servicesImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.repositories.UserRepository;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // in this we have to load our user.
        User user = repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email :" + username));
        return user;

    }

}
