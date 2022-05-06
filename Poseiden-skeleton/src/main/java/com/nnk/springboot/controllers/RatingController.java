package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.RatingService;
import com.nnk.springboot.services.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Controller specifically for managing the {@link Rating}.
 */
@Controller
public class RatingController {
    // DONE: Inject Rating service

    private final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);

    private final RatingService ratingService;

    /**
     * Create a new instance of this {@link RatingController}. This will be done automatically by SpringBoot with
     * dependencies injection.
     *
     * @param ratingService
     *         instance of {@link RatingService} .
     */
    public RatingController(RatingService ratingService) {

        this.ratingService = ratingService;
    }

    /**
     * Returns a view containing the list of {@link Rating} to the connected {@link User}.
     *
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        // DONE: find all Rating, add to model

        LOGGER.info("RatingController.class | Get Request: \"/rating/list\"");

        model.addAttribute("ratings", this.ratingService.findAll());
        return "rating/list";
    }

    /**
     * Returns a view containing the form for adding a {@link Rating} to a connected {@link User}.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {

        LOGGER.info("RatingController.class | Get Request: \"/rating/add\"");

        return "rating/add";
    }

    /**
     * Retrieves a model containing a {@link Rating} to add to database from a {@link User} connected.
     *
     * @param rating
     *         to add to database.
     * @param result
     *         is an interface that dictates how the object that stores the validation result.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return Rating list

        LOGGER.info("RatingController.class | Post Request: \"/rating/validate\"");

        if (!result.hasErrors()) {

            ratingService.save(rating);
            model.addAttribute("rating", ratingService.findAll());
            return "redirect:/rating/list";
        }

        return "rating/add";
    }

    /**
     * Returns a view containing the pre-populated form for editing the {@link Rating} of the corresponding id for a
     * logged in {@link User}.
     *
     * @param id
     *         of the {@link Rating} to be modified.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get Rating by Id and to model then show to the form

        LOGGER.info("RatingController.class | Get Request: \"/rating/update/{}\"", id);

        model.addAttribute("rating", ratingService.findById(id));
        return "rating/update";
    }

    /**
     * Returns a view based on whether the {@link Rating} is correct to a {@link Rating} for a connected
     * {@link User}.
     *
     * @param id
     *         of the {@link Rating} to be modified.
     * @param rating
     *         to be modified
     * @param result
     *         is an interface that dictates how the object that stores the validation result.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update Rating and return Rating list

        LOGGER.info("RatingController.class | Post Request: \"/rating/update/{}\"", id);

        if (result.hasErrors()) {
            return "rating/update";
        }

        this.ratingService.save(rating);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }

    /**
     * Returns a view containing the list of {@link Rating} after deleting the {@link Rating} corresponding to the id
     * if it exists at the connected {@link User}.
     *
     * @param id
     *         of the {@link Rating} to be deleted.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // DONE: Find Rating by Id and delete the Rating, return to Rating list

        LOGGER.info("RatingController.class | Get Request: \"/rating/delete/{}\"", id);
        
        this.ratingService.deleteById(id);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }
}