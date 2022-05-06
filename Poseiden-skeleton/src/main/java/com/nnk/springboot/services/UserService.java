package com.nnk.springboot.services;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Intended to contain the business code for {@link User}. It implements the {@link UserDetailsService} interface.
 */
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

    /**
     * Return the list of all {@link User} in database
     *
     * @return a {@link List} of {@link User}
     */
    public List<User> findAll() {

        return this.userRepository.findAll();
    }

    /**
     * Search for a user by an id.
     *
     * @param id
     *         internal identifier of a {@link User}
     *
     * @return an {@link Optional} {@link User}
     */
    public User findById(Integer id) {

        return this.userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Invalid user Id:" + id));
    }

    /**
     * Requests the database registration of a {@link User}.
     *
     * @param user
     *         to be registered in database
     *
     * @return the registered user.
     */
    public User save(User user) {

        return this.userRepository.save(user);
    }

    /**
     * Request the deletion of a {@link User}
     *
     * @param id
     *         matches the {@link User} to be deleted
     */
    public void deleteById(Integer id) {

        if (!this.userRepository.findById(id).isPresent()) {

            throw new NoSuchElementException("Invalid user Id:" + id);
        }

        this.userRepository.deleteById(id);
    }
}
