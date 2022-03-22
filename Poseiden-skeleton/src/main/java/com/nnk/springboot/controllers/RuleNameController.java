package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {
    // DONE: Inject RuleName service

    private final Logger LOGGER = LoggerFactory.getLogger(RuleNameController.class);

    private final RuleNameService ruleNameService;

    public RuleNameController(RuleNameService ruleNameService) {

        this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        // DONE: find all RuleName, add to model

        LOGGER.info("RuleNameController.class | Get Request: \"/ruleName/list\"");

        model.addAttribute("ruleNames", this.ruleNameService.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName id) {

        LOGGER.info("RuleNameController.class | Get Request: \"/ruleName/add\"");

        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return RuleName list

        LOGGER.info("RuleNameController.class | Post Request: \"/ruleName/validate\"");

        if (!result.hasErrors()) {

            ruleNameService.save(ruleName);
            model.addAttribute("ruleNames", ruleNameService.findAll());
            return "redirect:/ruleName/list";
        }

        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get RuleName by Id and to model then show to the form

        LOGGER.info("RuleNameController.class | Get Request: \"/ruleName/update/{}\"", id);

        model.addAttribute("ruleName", ruleNameService.findById(id));
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update RuleName and return RuleName list

        LOGGER.info("RuleNameController.class | Post Request: \"/ruleName/update/{}\"", id);

        if (result.hasErrors()) {
            return "/ruleName/update";
        }

        this.ruleNameService.save(ruleName);
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // DONE: Find RuleName by Id and delete the RuleName, return to Rule list

        LOGGER.info("RuleNameController.class | Get Request: \"/ruleName/delete/{}\"", id);

        this.ruleNameService.deleteById(id);
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }
}
