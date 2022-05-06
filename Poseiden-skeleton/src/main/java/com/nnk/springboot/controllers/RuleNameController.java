package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
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

/**
 * Controller specifically for managing the {@link RuleName}.
 */
@Controller
public class RuleNameController {
    // DONE: Inject RuleName service

    private final Logger LOGGER = LoggerFactory.getLogger(RuleNameController.class);

    private final RuleNameService ruleNameService;

    /**
     * Create a new instance of this {@link RuleNameController}. This will be done automatically by SpringBoot with
     * dependencies injection.
     *
     * @param ruleNameService
     *         instance of {@link RuleNameService} .
     */
    public RuleNameController(RuleNameService ruleNameService) {

        this.ruleNameService = ruleNameService;
    }

    /**
     * Returns a view containing the list of {@link RuleName} to the connected {@link User}.
     *
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        // DONE: find all RuleName, add to model

        LOGGER.info("RuleNameController.class | Get Request: \"/ruleName/list\"");

        model.addAttribute("ruleNames", this.ruleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * Returns a view containing the form for adding a {@link RuleName} to a connected {@link User}.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName id) {

        LOGGER.info("RuleNameController.class | Get Request: \"/ruleName/add\"");

        return "ruleName/add";
    }

    /**
     * Retrieves a model containing a {@link RuleName} to add to database from a {@link User} connected.
     *
     * @param ruleName
     *         to add to database.
     * @param result
     *         is an interface that dictates how the object that stores the validation result.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
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

    /**
     * Returns a view containing the pre-populated form for editing the {@link RuleName} of the corresponding id for a
     * logged in {@link User}.
     *
     * @param id
     *         of the {@link RuleName} to be modified.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get RuleName by Id and to model then show to the form

        LOGGER.info("RuleNameController.class | Get Request: \"/ruleName/update/{}\"", id);

        model.addAttribute("ruleName", ruleNameService.findById(id));
        return "ruleName/update";
    }

    /**
     * Returns a view based on whether the {@link RuleName} is correct to a {@link RuleName} for a connected
     * {@link User}.
     *
     * @param id
     *         of the {@link RuleName} to be modified.
     * @param ruleName
     *         to be modified
     * @param result
     *         is an interface that dictates how the object that stores the validation result.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update RuleName and return RuleName list

        LOGGER.info("RuleNameController.class | Post Request: \"/ruleName/update/{}\"", id);

        if (result.hasErrors()) {
            return "ruleName/update";
        }

        this.ruleNameService.save(ruleName);
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    /**
     * Returns a view containing the list of {@link RuleName} after deleting the {@link RuleName} corresponding to the
     * id if it exists at the connected {@link User}.
     *
     * @param id
     *         of the {@link RuleName} to be deleted.
     * @param model
     *         provides the attributes used by the views.
     *
     * @return a {@link String} that refers to the name of a view.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // DONE: Find RuleName by Id and delete the RuleName, return to Rule list

        LOGGER.info("RuleNameController.class | Get Request: \"/ruleName/delete/{}\"", id);

        this.ruleNameService.deleteById(id);
        model.addAttribute("ruleNames", ruleNameService.findAll());
        return "redirect:/ruleName/list";
    }
}
