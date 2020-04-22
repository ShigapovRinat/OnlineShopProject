package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.shop.dto.SignUpDto;
import ru.itis.shop.services.SignUpService;

@Profile("mvc")
@Controller
@RequestMapping("/signUp")
public class SignUpController {

    @Autowired
    private SignUpService service;

    @GetMapping
    public String openPage(Authentication authentication) {
        if(authentication == null) {
            return "sign_up";
        } else {
            return "redirect:/profile";
        }
    }

    @PostMapping
    public ModelAndView registration(SignUpDto dto) {
        try {
            if (dto.getEmail().equals("") || dto.getPassword().equals("")
                    || dto.getName().equals("")) throw new IllegalArgumentException("Заполните все параметры");
            service.signUp(dto);
            return new ModelAndView("redirect:/signIn");
        }catch (IllegalArgumentException e){
            return new ModelAndView("sign_up", "exception", e);
        }
    }

}
