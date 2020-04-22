package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.shop.services.ConfirmService;

@Profile("mvc")
@Controller
@RequestMapping("/confirm/{link}")
public class ConfirmController {

    @Autowired
    private ConfirmService confirmService;

    @GetMapping
    public ModelAndView openPage(@PathVariable("link") String link){
        confirmService.isConfirmed(link);
        return new ModelAndView("confirmed_success");
    }
}
