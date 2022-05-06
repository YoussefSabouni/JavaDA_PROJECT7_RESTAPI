package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.SecureRandom;

/**
 * Controller specifically for managing the {@link User}.
 */
@Controller
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    /**
     * Create a new instance of this {@link UserController}. This will be done automatically by SpringBoot with
     * dependencies injection.
     *
     * @param userService
     *         instance of {@link UserService} .
     */
    public UserController(UserService userService) {

        this.userService = userService;
    }

    /**
     * Returns a view containing the list of {@link User} to the connected {@link User}.
     *
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @RequestMapping("/user/list")
    public String home(Model model) {

        LOGGER.info("UserController.class | Get Request: \"/user/list\"");

        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * Returns a view containing the form for adding a {@link User} to a connected {@link User}.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/user/add")
    public String addUser(User bid) {

        LOGGER.info("UserController.class | Get Request: \"/user/add\"");

        return "user/add";
    }

    /**
     * Retrieves a model containing a {@link User} to add to database from a {@link User} connected.
     *
     * @param user
     *         to add to database.
     * @param result
     *         is an interface that dictates how the object that stores the validation result.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {

        LOGGER.info("UserController.class | Post Request: \"/user/validate\"");

        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom());
            user.setPassword(encoder.encode(user.getPassword()));
            userService.save(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     * Returns a view containing the pre-populated form for editing the {@link User} of the corresponding id for a
     * logged in {@link User}.
     *
     * @param id
     *         of the {@link User} to be modified.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        LOGGER.info("UserController.class | Get Request: \"/user/update/{}\"", id);

        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * Returns a view based on whether the {@link User} is correct to a {@link User} for a connected {@link User}.
     *
     * @param id
     *         of the {@link User} to be modified.
     * @param user
     *         to be modified
     * @param result
     *         is an interface that dictates how the object that stores the validation result.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {

        LOGGER.info("UserController.class | Post Request: \"/user/update/{}\"", id);

        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, new SecureRandom());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userService.save(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }

    /**
     * Returns a view containing the list of {@link User} after deleting the {@link User} corresponding to the id if it
     * exists at the connected {@link User}.
     *
     * @param id
     *         of the {@link User} to be deleted.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {

        LOGGER.info("UserController.class | Get Request: \"/user/delete/{}\"", id);

        User user = userService.findById(id);
        userService.deleteById(user.getId());
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }
}
