package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.shop.services.UsersService;

@Profile("mvc")
@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteUser/{user-id}")
    public String deletePerson(@PathVariable("user-id") Long id) {
        usersService.deleteUserById(id);
        return "redirect:/allUsers";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/allUsers")
    public ModelAndView showAllPersons(){
        return new ModelAndView("all_users", "users", usersService.findAllUsers());
    }


}
