package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Specific controller for redirecting {@link User} when it comes to the root URL of the site.
 */
@Controller
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    public String home(Model model) {

        LOGGER.info("HomeController.class | Get Request: \"/\"");

        return "home";
    }

    @RequestMapping("/admin/home")
    public String adminHome(Model model) {

        LOGGER.info("HomeController.class | Get Request: \"/admin/home\"");

        return "redirect:/bidList/list";
    }
}
