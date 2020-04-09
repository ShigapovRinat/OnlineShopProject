package ru.itis.onlineShop.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.onlineShop.models.Person;
import ru.itis.onlineShop.security.details.PersonDetailsImpl;

@Controller
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    @GetMapping("/profile")
    public String getProfilePage(Authentication authentication, Model model){
        PersonDetailsImpl userDetails = (PersonDetailsImpl) authentication.getPrincipal();
        Person person = userDetails.getPerson();
        model.addAttribute("person", person);
        return "profile";
    }
}
