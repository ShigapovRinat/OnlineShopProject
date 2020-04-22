package ru.itis.shop.controllers.mvc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Profile("mvc")
@Controller
public class RootController {

    @GetMapping("/")
    public String getRootPage() {
        return "redirect:/main";
    }
}
