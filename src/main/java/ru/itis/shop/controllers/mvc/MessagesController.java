package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.shop.dto.CustomScopeData;
import ru.itis.shop.security.mvc.details.UserDetailsImpl;
import ru.itis.shop.dto.MessageDto;
import ru.itis.shop.services.MessagesService;
import ru.itis.shop.services.UsersService;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("mvc")
@RestController
public class MessagesController {

    @Value("${admin.email}")
    private String adminEmail;

    private static final Map<String, List<MessageDto>> messages = new HashMap<>();

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/messages")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto message, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!messages.containsKey(message.getPageId())) {
            messages.put(message.getPageId(), new ArrayList<>());
        }
        if (message.getWhomId() == null){
            message.setWhomId(1L);
        }
        message.setFromId(userDetails.getUser().getId());

        if (!(message.getText().equals("Здравствуйте, чем я могу Вам помочь?") || message.getText().equals(""))) {
            messagesService.save(message);
        }
        for (String pageId : messages.keySet()) {
            if (pageId.equals(usersService.findPersonById(message.getWhomId()).getEmail())
                    || pageId.equals(adminEmail)
                    || pageId.equals(message.getPageId())) {
                synchronized (messages.get(pageId)) {
                    messages.get(pageId).add(message);
                    messages.get(pageId).notifyAll();
                }
            }
        }
        return ResponseEntity.ok().build();
    }


    @GetMapping("/messages")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageDto>> getMessagesForPage(@RequestParam("pageId") String pageId, Authentication authentication) {
        synchronized (messages.get(pageId)) {
            if (messages.get(pageId).isEmpty()) {
                try {
                    messages.get(pageId).wait();
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        List<MessageDto> response = new ArrayList<>(messages.get(pageId));
        messages.get(pageId).clear();
        return ResponseEntity.ok(response);
    }
}