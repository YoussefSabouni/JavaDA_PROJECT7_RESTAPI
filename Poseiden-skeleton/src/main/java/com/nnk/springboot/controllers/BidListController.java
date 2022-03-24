package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
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
import java.security.Principal;


@Controller
public class BidListController {
    // DONE: Inject Bid service

    private final Logger LOGGER = LoggerFactory.getLogger(BidListController.class);

    private final BidListService bidListService;

    public BidListController(BidListService bidListService) {

        this.bidListService = bidListService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model, Principal principal) {
        // DONE: call service find all bids to show to the view

        LOGGER.info("BidListController.class | Get Request: \"/bidList/list\"");

        model.addAttribute("bidLists", this.bidListService.findAll());

        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {

        LOGGER.info("BidListController.class | Get Request: \"/bidList/add\"");

        return "bidList/add";
    }

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

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get Bid by Id and to model then show to the form

        LOGGER.info("BidListController.class | Get Request: \"/bidList/update/{}\"", id);

        model.addAttribute("bidList", bidListService.findById(id));
        return "bidList/update";
    }

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

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // DONE: Find Bid by Id and delete the bid, return to Bid list

        LOGGER.info("BidListController.class | Get Request: \"/bidList/delete/{}\"", id);

        this.bidListService.deleteById(id);
        model.addAttribute("bidLists", bidListService.findAll());
        return "redirect:/bidList/list";
    }
}
