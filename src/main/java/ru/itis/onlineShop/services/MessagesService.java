package ru.itis.onlineShop.services;

import ru.itis.onlineShop.dto.MessageDto;

import java.util.List;

public interface MessagesService {
    List<MessageDto> findAll();
    List<MessageDto> findForPerson(Long idUser);
    void save(MessageDto messageDto);
}
