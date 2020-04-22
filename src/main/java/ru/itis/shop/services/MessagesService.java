package ru.itis.shop.services;

import ru.itis.shop.dto.MessageDto;

import java.util.List;

public interface MessagesService {
    List<MessageDto> findAll();
    List<MessageDto> findForPerson(Long idUser);
    void save(MessageDto messageDto);
}
