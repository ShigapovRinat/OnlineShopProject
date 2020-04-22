package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.shop.services.PersonsService;

@Profile("mvc")
@Controller
public class AdminPersonsController {

    @Autowired
    private PersonsService personsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deletePerson/{person-id}")
    public String deletePerson(@PathVariable("person-id") Long id) {
        personsService.deletePersonById(id);
        return "redirect:/allPersons";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allPersons")
    public ModelAndView showAllPersons(){
        return new ModelAndView("all_persons", "persons", personsService.findAllPersons());
    }


}
