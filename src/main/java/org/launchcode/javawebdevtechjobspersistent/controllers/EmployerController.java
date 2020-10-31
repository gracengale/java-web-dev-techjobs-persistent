package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.data.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class EmployerController {

    @Autowired
    private EmployerRepository employerRepository;

    @GetMapping("employers/add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("employers/add")
    public String processAddEmployerForm(@ModelAttribute @Valid Employer employer,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Employer");
            return "employers/add";
        }

        employerRepository.save(employer);
        model.addAttribute("employers", employerRepository.findAll());
        return "employers/index";
    }

    @GetMapping("employers/view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable Integer employerId) {

            Optional<Employer> optEmployer = employerRepository.findById(employerId);
            if (optEmployer.isEmpty()) {
                model.addAttribute("employers", employerRepository.findAll());
                return "employers/index";
            } else {
                Employer employer = optEmployer.get();
                model.addAttribute("title", employer.getName());
                model.addAttribute("employer", employer);
                return "employers/view";
            }
    }
}

