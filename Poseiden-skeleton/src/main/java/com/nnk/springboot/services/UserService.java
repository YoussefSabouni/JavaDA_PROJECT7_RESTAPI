package com.nnk.springboot.services;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public List<User> findAll() {

        return this.userRepository.findAll();
    }

    public User findById(Integer id) {

        return this.userRepository.findById(id)
                                     .orElseThrow(() -> new NoSuchElementException("Invalid user Id:" + id));
    }

    public User save(User user) {

        return this.userRepository.save(user);
    }

    public void deleteById(Integer id) {

        if (!this.userRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid user Id:" + id);
        }

        this.userRepository.deleteById(id);
    }
}
