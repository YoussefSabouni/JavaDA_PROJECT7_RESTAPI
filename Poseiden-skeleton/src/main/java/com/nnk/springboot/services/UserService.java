package com.nnk.springboot.services;


import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Create a new instance of this {@link UserService}. This will be done automatically by SpringBoot with
     * dependencies injection.
     *
     * @param userRepository
     *         instance of {@link UserRepository}.
     */
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.userRepository.findByUsername(username);
    }
}
