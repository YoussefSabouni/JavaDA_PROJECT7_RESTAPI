package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.BidListService;
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
 * Controller specifically for managing the {@link BidList}.
 */
@Controller
public class BidListController {
    // DONE: Inject Bid service

    private final Logger LOGGER = LoggerFactory.getLogger(BidListController.class);

    private final BidListService bidListService;

    /**
     * Create a new instance of this {@link BidListController}. This will be done automatically by SpringBoot with
     * dependencies injection.
     *
     * @param bidListService
     *         instance of {@link BidListService} .
     */
    public BidListController(BidListService bidListService) {

        this.bidListService = bidListService;
    }

    /**
     * Returns a view containing the list of {@link BidList} to the connected {@link User}.
     *
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        // DONE: call service find all bids to show to the view

        LOGGER.info("BidListController.class | Get Request: \"/bidList/list\"");

        model.addAttribute("bidLists", this.bidListService.findAll());

        return "bidList/list";
    }

    /**
     * Returns a view containing the form for adding a {@link BidList} to a connected {@link User}.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {

        LOGGER.info("BidListController.class | Get Request: \"/bidList/add\"");

        return "bidList/add";
    }

    /**
     * Retrieves a model containing a {@link BidList} to add to database from a {@link User} connected.
     *
     * @param bidList
     *         to add to database.
     * @param result
     *         is an interface that dictates how the object that stores the validation result.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return bid list+

        LOGGER.info("BidListController.class | Post Request: \"/bidList/validate\"");

        if (!result.hasErrors()) {
            bidListService.save(bidList);
            model.addAttribute("bidLists", bidListService.findAll());
            return "redirect:/bidList/list";
        }

        return "bidList/add";
    }

    /**
     * Returns a view containing the pre-populated form for editing the {@link BidList} of the corresponding id for a
     * logged in {@link User}.
     *
     * @param id
     *         of the {@link BidList} to be modified.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get Bid by Id and to model then show to the form

        LOGGER.info("BidListController.class | Get Request: \"/bidList/update/{}\"", id);

        model.addAttribute("bidList", bidListService.findById(id));
        return "bidList/update";
    }

    /**
     * Returns a view based on whether the {@link BidList} is correct to a {@link BidList} for a connected
     * {@link User}.
     *
     * @param id
     *         of the {@link BidList} to be modified.
     * @param bidList
     *         to be modified
     * @param result
     *         is an interface that dictates how the object that stores the validation result.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update Bid and return list Bid

        LOGGER.info("BidListController.class | Post Request: \"/bidList/update/{}\"", id);

        if (result.hasErrors()) {
            return "bidList/update";
        }

        this.bidListService.save(bidList);
        model.addAttribute("bidLists", bidListService.findAll());
        return "redirect:/bidList/list";
    }

    /**
     * Returns a view containing the list of {@link BidList} after deleting the {@link BidList} corresponding to the id
     * if it exists at the connected {@link User}.
     *
     * @param id
     *         of the {@link BidList} to be deleted.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // DONE: Find Bid by Id and delete the bid, return to Bid list

        LOGGER.info("BidListController.class | Get Request: \"/bidList/delete/{}\"", id);

        this.bidListService.deleteById(id);
        model.addAttribute("bidLists", bidListService.findAll());
        return "redirect:/bidList/list";
    }
}