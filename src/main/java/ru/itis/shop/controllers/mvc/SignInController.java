package ru.itis.shop.controllers.mvc;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("mvc")
@Controller
public class SignInController {


    @GetMapping("/signIn")
    public String getPage(Authentication authentication) {
        if(authentication == null) {
            return "sign_in";
        } else {
            return "redirect:/profile";
        }
    }
}
