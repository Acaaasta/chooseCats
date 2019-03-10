package com.acasssta.chooseCats.controllers;

import com.acasssta.chooseCats.entities.Cat;
import com.acasssta.chooseCats.entities.SystemUser;
import com.acasssta.chooseCats.entities.User;
import com.acasssta.chooseCats.entities.UserPrincipal;
import com.acasssta.chooseCats.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
public class MainController {

    private CatService catService;

    @Autowired
    public void setCatService(CatService catService) {
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
    public String showHomePage(Model model, Principal principal) {
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
                              //@ModelAttribute("cats") List<Cat> twoCats,
                              Principal principal, @RequestParam("selectedCat") Cat selectedCat) {
        //List<Cat> twoCats = (List<Cat>) model.asMap().get("cats");
        catService.IncreaseRating(selectedCat, principal.getName());

        return "redirect:/start";
    }

}
