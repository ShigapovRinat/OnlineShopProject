package ru.itis.onlineShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.onlineShop.services.PersonsService;

@Controller
public class AdminPersonsController {

    @Autowired
    private PersonsService personsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deletePerson/{person-id}")
    public String deletePerson(@PathVariable("person-id") String email) {
        personsService.deletePerson(email);
        return "redirect:/all_persons";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allPersons")
    public ModelAndView showAllPersons(){
        return new ModelAndView("all_persons", "persons", personsService.findAllPersons());
    }


}
