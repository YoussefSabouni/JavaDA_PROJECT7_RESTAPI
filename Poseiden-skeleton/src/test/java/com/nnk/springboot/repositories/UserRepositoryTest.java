package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserService userService;

    @Test
    public void userServiceTest() {

        User user = new User();
        user.setUsername("User");
        user.setPassword("Password51!");
        user.setFullName("User FullName");
        user.setRole("USER");

        // Save
        user = userService.save(user);
        Assert.assertNotNull(user.getId());
        Assert.assertEquals("User", user.getUsername());
        Assert.assertEquals("User FullName", user.getFullName());
        Assert.assertEquals("USER", user.getRole());

        // Update
        String oldPassword = user.getPassword();
        user.setPassword("iunergoiujnerg561225@!");
        user = userService.save(user);
        Assert.assertNotEquals(oldPassword, user.getPassword());

        // Find
        List<User> listResult = userService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = user.getId();
        userService.deleteById(id);
        Assertions.assertThrows(NoSuchElementException.class, () -> userService.findById(id));
    }
}
