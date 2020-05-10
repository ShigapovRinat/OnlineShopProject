package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.shop.dto.NameDto;
import ru.itis.shop.dto.PasswordDto;
import ru.itis.shop.dto.UserDto;
import ru.itis.shop.models.User;
import ru.itis.shop.security.mvc.details.UserDetailsImpl;
import ru.itis.shop.services.ProfileService;
import ru.itis.shop.services.UsersService;

import javax.validation.Valid;

@Profile("mvc")
@Controller
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UsersService usersService;

    @GetMapping("/profile")
    public String getProfilePage(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User authUser = userDetails.getUser();
        UserDto user = usersService.findPerson(authUser.getEmail());
        model.addAttribute("user", user);
        model.addAttribute("nameDto", new NameDto());
        model.addAttribute("passwordDto", new PasswordDto());
        return "profile";
    }

    @PostMapping("/profile/changeName")
    public ModelAndView registration(@Valid NameDto nameDto, BindingResult bindingResult, Authentication authentication) {
        if (!bindingResult.hasErrors()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userDetails.getUser();
            nameDto.setEmail(user.getEmail());
            profileService.changeName(nameDto);
            return new ModelAndView("redirect:/profile");
        } else {
            return new ModelAndView("redirect:/profile?error");
        }
    }

    @PostMapping("/profile/changePassword")
    public ModelAndView registration(@Valid PasswordDto passwordDto, BindingResult bindingResult, Authentication authentication) {
        if (!bindingResult.hasErrors()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userDetails.getUser();
            passwordDto.setEmail(user.getEmail());
            profileService.changePassword(passwordDto);
            return new ModelAndView("redirect:/profile");
        } else {
            return new ModelAndView("redirect:/profile?error");
        }
    }
}
