package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
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

@Controller
public class TradeController {
    // DONE: Inject Trade service

    private final Logger LOGGER = LoggerFactory.getLogger(TradeController.class);

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {

        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
        // DONE: find all Trade, add to model

        LOGGER.info("TradeController.class | Get Request: \"/trade/list\"");

        model.addAttribute("trades", this.tradeService.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {

        LOGGER.info("TradeController.class | Get Request: \"/trade/add\"");

        return "trade/add";
    }

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

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get Trade by Id and to model then show to the form

        LOGGER.info("TradeController.class | Get Request: \"/trade/update/{}\"", id);

        model.addAttribute("trade", this.tradeService.findById(id));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update Trade and return Trade list

        LOGGER.info("TradeController.class | Post Request: \"/trade/update/{}\"", id);

        if (result.hasErrors()) {
            return "/trade/update";
        }

        this.tradeService.save(trade);
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // DONE: Find Trade by Id and delete the Trade, return to Trade list

        LOGGER.info("TradeController.class | Get Request: \"/trade/delete/{}\"", id);

        this.tradeService.deleteById(id);
        model.addAttribute("trade", this.tradeService.findAll());
        return "redirect:/trade/list";
    }
}
