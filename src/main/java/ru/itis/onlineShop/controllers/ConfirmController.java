package ru.itis.onlineShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.onlineShop.services.ConfirmService;


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
