package ru.itis.onlineShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.onlineShop.dto.MessageDto;
import ru.itis.onlineShop.security.details.PersonDetailsImpl;
import ru.itis.onlineShop.services.MessagesService;
import ru.itis.onlineShop.services.PersonsService;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessagesController {
    private static final Map<String, List<MessageDto>> messages = new HashMap<>();

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private PersonsService personsService;

    // получили сообщение от какой либо страницы - мы его разошлем во все другие страницы
    @PostMapping("/messages")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> receiveMessage(@RequestBody MessageDto message, Authentication authentication) {

        PersonDetailsImpl userDetails = (PersonDetailsImpl) authentication.getPrincipal();
        // если сообщений с этой или для этой страницы еще не было
        if (!messages.containsKey(message.getPageId())) {
            // добавляем эту страницу в Map-у страниц
            messages.put(message.getPageId(), new ArrayList<>());
        }
        if (message.getWhomId() == null){
            message.setWhomId(1L);
        }
        message.setFromId(userDetails.getPerson().getId());
        if (!(message.getText().equals("Здравствуйте, чем я могу Вам помочь?") || message.getText().equals(""))) {
            messagesService.save(message);
        }
        // полученное сообщение добавляем для всех открытых страниц нашего приложения
        for (String pageId : messages.keySet()) {
            if (pageId.equals(personsService.findPersonById(message.getWhomId()).getEmail())
                    || pageId.equals("89179060010@mail.ru")
                    || pageId.equals(message.getPageId())) {
                // перед тем как положить сообщение для какой-либо страницы
                // мы список сообщений блокируем
                synchronized (messages.get(pageId)) {
                    // добавляем сообщение
                    messages.get(pageId).add(message);
                    // говорим, что другие потоки могут воспользоваться этим списком
                    messages.get(pageId).notifyAll();
                }
            }
        }
        return ResponseEntity.ok().build();
    }


    // получить все сообщения для текущего запроса
    @GetMapping("/messages")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MessageDto>> getMessagesForPage(@RequestParam("pageId") String pageId, Authentication authentication) {
        PersonDetailsImpl userDetails = (PersonDetailsImpl) authentication.getPrincipal();
        // получили список сообшений для страницы и заблокировали его
        synchronized (messages.get(pageId)) {
            // если нет сообщений уходим в ожидание
            if (messages.get(pageId).isEmpty()) {
                try {
                    messages.get(pageId).wait();
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        // если сообщения есть - то кладем их в новых список
        List<MessageDto> response = new ArrayList<>(messages.get(pageId));
        // удаляем сообщения из мапы
        messages.get(pageId).clear();
        return ResponseEntity.ok(response);
    }
}