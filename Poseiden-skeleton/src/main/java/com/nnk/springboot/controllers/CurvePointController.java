package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurvePointController {
    // DONE: Inject Curve Point service

    private final Logger LOGGER = LoggerFactory.getLogger(CurvePointController.class);


    private final CurvePointService curvePointService;

    public CurvePointController(CurvePointService curvePointService) {

        this.curvePointService = curvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        // DONE: find all Curve Point, add to model

        LOGGER.info("CurvePointController.class | Get Request: \"/curvePoint/list\"");

        model.addAttribute("curvePoints", this.curvePointService.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {

        LOGGER.info("CurvePointController.class | Get Request: \"/curvePoint/add\"");

        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // DONE: check data valid and save to db, after saving return Curve list

        LOGGER.info("CurvePointController.class | Post Request: \"/curvePoint/validate\"");

        if (!result.hasErrors()) {

            curvePointService.save(curvePoint);
            model.addAttribute("curvePoints", curvePointService.findAll());
            return "redirect:/curvePoint/list";
        }

        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // DONE: get CurvePoint by Id and to model then show to the form

        LOGGER.info("CurvePointController.class | Get Request: \"/curvePoint/update/{}\"", id);

        model.addAttribute("curvePoint", curvePointService.findById(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // DONE: check required fields, if valid call service to update Curve and return Curve list

        LOGGER.info("CurvePointController.class | Post Request: \"curvePoint/list\"");

        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        this.curvePointService.save(curvePoint);
        model.addAttribute("curvePoint", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // DONE: Find Curve by Id and delete the Curve, return to Curve list

        LOGGER.info("CurvePointController.class | Post Request: \"/curvePoint/list\"");

        this.curvePointService.deleteById(id);
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "redirect:/curvePoint/list";
    }
}
