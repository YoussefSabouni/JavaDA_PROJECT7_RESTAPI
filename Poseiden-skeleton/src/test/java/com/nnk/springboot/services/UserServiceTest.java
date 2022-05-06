package com.nnk.springboot.services;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Autowired
    private UserService    userService;
    @MockBean
    private UserRepository userRepository;
    private User           user;

    @BeforeEach
    private void setUpPerTest() {

        user = new User();
        user.setUsername("User");
        user.setPassword("Password51!");
        user.setFullName("User FullName");
        user.setRole("USER");
        Mockito.reset(userRepository);
    }

    @Test
    public void saveUser_shouldReturnUserSaved_forCorrectUser() {

        User userSaved = new User();
        userSaved.setUsername("User");
        userSaved.setPassword("Password51!");
        userSaved.setFullName("User FullName");
        userSaved.setRole("USER");
        userSaved.setId(1);
        when(userRepository.save(eq(user))).thenReturn(userSaved);

        user = userService.save(user);
        verify(userRepository, times(1)).save(any());
        Assertions.assertEquals(userSaved, user);
    }

    @Test
    public void updateUser_shouldReturnUserUpdated_forCorrectUser() {

        User userUpdated = new User();
        userUpdated.setUsername("User Updated");
        userUpdated.setPassword("Password51!");
        userUpdated.setFullName("User FullName");
        userUpdated.setRole("USER");
        userUpdated.setId(1);

        user.setId(1);
        user.setUsername("User Updated");

        when(userRepository.save(eq(user))).thenReturn(userUpdated);

        user = userService.save(user);

        verify(userRepository, times(1)).save(any());
        Assertions.assertEquals(userUpdated, user);
    }

    @Test
    public void findAll_shouldReturnAllUserInDatabase() {

        user.setId(1);
        List<User> users = new ArrayList<User>() {{this.add(user);}};

        for (int i = 0 ; i < 4 ; i++) {

            user.setId(user.getId() + 1);
            users.add(user);
        }

        when(userRepository.findAll()).thenReturn(users);

        List<User> listResult = userService.findAll();

        Assertions.assertEquals(users.size(), listResult.size());
        Assertions.assertTrue(listResult.contains(user));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void findById_shouldReturnOneUser_whenUserIdIsExists() {

        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User userFound = userService.findById(user.getId());

        verify(userRepository, times(1)).findById(any());
        Assertions.assertEquals(user, userFound);
    }

    @Test
    public void findById_shouldThrowException_whenUserIdIsNotExists() {

        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> userService.findById(1));
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void deleteById_shouldDeleteUser() {


        when(this.userRepository.findById(eq(1))).thenReturn(Optional.of(user));
        Integer id = 1;
        userService.deleteById(id);

        when(this.userRepository.findById(eq(1))).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> userService.deleteById(id));
        verify(this.userRepository, times(2)).findById(1);
    }
}
