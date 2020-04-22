package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.shop.security.mvc.details.PersonDetailsImpl;
import ru.itis.shop.services.MessagesService;

@Profile("mvc")
@Controller
public class ChatController {

    @Autowired
    private MessagesService messagesService;

    @GetMapping("/support_chat")
    @PreAuthorize("isAuthenticated()")
    public String getChatPage(Model model, Authentication authentication) {
        PersonDetailsImpl userDetails = (PersonDetailsImpl) authentication.getPrincipal();
        boolean isAdmin = userDetails.getAuthorities().stream().findFirst().get().toString().equals("ADMIN");
        if (isAdmin){
            model.addAttribute("messages", messagesService.findAll());
        } else {
            model.addAttribute("messages", messagesService.findForPerson(userDetails.getPerson().getId()));
        }
        model.addAttribute("pageId", userDetails.getUsername());
        model.addAttribute("is_admin", isAdmin);
        return "support_chat";
    }
}