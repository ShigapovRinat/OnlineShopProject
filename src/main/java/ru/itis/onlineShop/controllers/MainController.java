package ru.itis.onlineShop.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping("/main")
    public ModelAndView getRootPage(Authentication authentication){
        if(authentication != null) {
            return new ModelAndView("main", "auth",
                    authentication.getAuthorities().toArray()[0].toString());
        } else return new ModelAndView("main", "auth", null);
    }
}
