package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Specific controller for the {@link User} connection management.
 */
@Controller
@RequestMapping("app")
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("login")
    public ModelAndView login() {

        LOGGER.info("LoginController.class | Get Request: \"/login\"");

        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.addObject("user", new User());
        return mav;
    }

    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {

        LOGGER.info("LoginController.class | Get Request: \"/secure/article-details\"");

        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    @GetMapping("error")
    public ModelAndView error() {

        LOGGER.info("LoginController.class | Get Request: \"/error\"");

        ModelAndView mav          = new ModelAndView();
        String       errorMessage = "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }
}
