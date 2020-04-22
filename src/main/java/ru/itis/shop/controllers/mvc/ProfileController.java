package ru.itis.shop.controllers.mvc;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.shop.models.Person;
import ru.itis.shop.security.mvc.details.PersonDetailsImpl;

@Profile("mvc")
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
