package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Employer;
import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("skills/add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute("title", "Add Skill");
        model.addAttribute(new Skill());
        return "skills/add";
    }

    @PostMapping("skills/add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill,
                                      Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Skill");
            return "skills/add";
        }

        skillRepository.save(newSkill);
        model.addAttribute("skills", skillRepository.findAll());
        return "skills/index";
    }

    @GetMapping("skills/view/{skillId}")
    public String displayViewSkill(Model model, @PathVariable Integer skillId) {

        Optional<Skill> optSkill = skillRepository.findById(skillId);
        if (optSkill.isEmpty()) {
            model.addAttribute("skills", skillRepository.findAll());
            return "skills/index";
        } else {
            Skill skill = optSkill.get();
            model.addAttribute("title", skill.getName());
            model.addAttribute("skill", skill);
            return "skills/view";
        }
    }

}

