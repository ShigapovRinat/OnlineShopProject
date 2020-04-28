package ru.itis.shop.controllers.mvc;

import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.shop.models.User;
import ru.itis.shop.security.mvc.details.UserDetailsImpl;

@Profile("mvc")
@Controller
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    @GetMapping("/profile")
    public String getProfilePage(Authentication authentication, Model model){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "profile";
    }
}
