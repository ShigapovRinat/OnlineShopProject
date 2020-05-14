package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.shop.dto.SignUpDto;
import ru.itis.shop.services.SignUpService;

import javax.validation.Valid;
import java.util.UUID;

@Profile("mvc")
@Controller
@RequestMapping("/signUp")
public class SignUpController {

    @Autowired
    private SignUpService service;

    @GetMapping
    public String openPage(Authentication authentication, Model model) {
        if(authentication == null) {
            model.addAttribute("signUpDto", new SignUpDto());
            return "sign_up";
        } else {
            return "redirect:/profile";
        }
    }

    @PostMapping
    public ModelAndView registration(@Valid SignUpDto signUpDto, BindingResult bindingResult) {
        try {
            if(!bindingResult.hasErrors()){
                service.signUp(signUpDto, UUID.randomUUID().toString());
                return new ModelAndView("redirect:/signIn");
            } else {
                return new ModelAndView("sign_up");
            }
        }catch (IllegalArgumentException e){
            return new ModelAndView("sign_up", "exception", e);
        }
    }

}
