package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.TradeService;
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
 * Controller specifically for managing the {@link Trade}.
 */
@Controller
public class TradeController {
    // DONE: Inject Trade service

    private final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);

    private final TradeService tradeService;

    /**
     * Create a new instance of this {@link TradeController}. This will be done automatically by SpringBoot with
     * dependencies injection.
     *
     * @param tradeService
     *         instance of {@link TradeService} .
     */
    public TradeController(TradeService tradeService) {

        this.tradeService = tradeService;
    }

    /**
     * Returns a view containing the list of {@link Trade} to the connected {@link User}.
     *
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        // DONE: find all Trade, add to model

        LOGGER.info("TradeController.class | Get Request: \"/trade/list\"");

        model.addAttribute("trades", this.tradeService.findAll());
        return "trade/list";
    }

    /**
     * Returns a view containing the form for adding a {@link Trade} to a connected {@link User}.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/trade/add")
    public String addUser(Trade bid) {

        LOGGER.info("TradeController.class | Get Request: \"/trade/add\"");

        return "trade/add";
    }

    /**
     * Retrieves a model containing a {@link Trade} to add to database from a {@link User} connected.
     *
     * @param trade
     *         to add to database.
     * @param result
     *         is an interface that dictates how the object that stores the validation result.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return Trade list

        LOGGER.info("TradeController.class | Post Request: \"/trade/validate\"");

        if (!result.hasErrors()) {
            tradeService.save(trade);
            model.addAttribute("trades", this.tradeService.findAll());
            return "redirect:/trade/list";
        }

        return "trade/add";
    }

    /**
     * Returns a view containing the pre-populated form for editing the {@link Trade} of the corresponding id for a
     * logged in {@link User}.
     *
     * @param id
     *         of the {@link Trade} to be modified.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get Trade by Id and to model then show to the form

        LOGGER.info("TradeController.class | Get Request: \"/trade/update/{}\"", id);

        model.addAttribute("trade", this.tradeService.findById(id));
        return "trade/update";
    }

    /**
     * Returns a view based on whether the {@link Trade} is correct to a {@link Trade} for a connected {@link User}.
     *
     * @param id
     *         of the {@link Trade} to be modified.
     * @param trade
     *         to be modified
     * @param result
     *         is an interface that dictates how the object that stores the validation result.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update Trade and return Trade list

        LOGGER.info("TradeController.class | Post Request: \"/trade/update/{}\"", id);

        if (result.hasErrors()) {
            return "trade/update";
        }

        this.tradeService.save(trade);
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }

    /**
     * Returns a view containing the list of {@link Trade} after deleting the {@link Trade} corresponding to the id if
     * it exists at the connected {@link User}.
     *
     * @param id
     *         of the {@link Trade} to be deleted.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // DONE: Find Trade by Id and delete the Trade, return to Trade list

        LOGGER.info("TradeController.class | Get Request: \"/trade/delete/{}\"", id);

        this.tradeService.deleteById(id);
        model.addAttribute("trade", this.tradeService.findAll());
        return "redirect:/trade/list";
    }
}
