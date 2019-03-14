package com.acasssta.chooseCats.controllers;

import com.acasssta.chooseCats.config.ResourceConfig;
import com.acasssta.chooseCats.entities.Cat;
import com.acasssta.chooseCats.entities.SystemUser;
import com.acasssta.chooseCats.services.CatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
public class MainController {

    private CatServiceImpl catService;

    @Autowired
    public void setCatService(CatServiceImpl catService) {
        this.catService = catService;
    }

    @GetMapping("/")
    public String showHomePage() {
        return "index";
    }

    @GetMapping("/rating")
    public String showRating(Model model){
        List<Cat> allCats=catService.sortCats();
        model.addAttribute("cats", allCats);
        return "rating";
    }

    @GetMapping("/start")
    public String startChoosing(Model model, Principal principal) {
        if (principal!=null) {
            List<Cat> twoUserCat = catService.getTwoCats(principal.getName());
            if (twoUserCat.size() != 0) {
                model.addAttribute("cats", twoUserCat);
                return "start";
            } else {
                return "redirect:/rating";
            }
        }else {
            model.addAttribute("systemUser", new SystemUser());
            return "registration-form";
        }
    }

    @PostMapping("/start")
    public String catSelected(Model model,
                              Principal principal, @RequestParam("selectedCat") Cat selectedCat) {
        catService.increaseRating(selectedCat, principal.getName());

        return "redirect:/start";
    }

}
