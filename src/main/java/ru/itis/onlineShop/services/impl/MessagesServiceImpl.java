package ru.itis.onlineShop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.onlineShop.dto.MessageDto;
import ru.itis.onlineShop.models.Message;
import ru.itis.onlineShop.repositories.MessagesRepository;
import ru.itis.onlineShop.services.MessagesService;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MessagesServiceImpl implements MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Override
    public List<MessageDto> findAll() {
        return MessageDto.from(messagesRepository.findAll());
    }

    @Override
    public List<MessageDto> findForPerson(Long idUser) {
        return MessageDto.from(messagesRepository.findForPerson(idUser));
    }

    @Override
    public void save(MessageDto messageDto) {
        messagesRepository.save(Message.builder()
                .text(messageDto.getText())
                .fromId(messageDto.getFromId())
                .whomId(messageDto.getWhomId())
                .createAt(LocalDateTime.now())
                .build());
    }
}
