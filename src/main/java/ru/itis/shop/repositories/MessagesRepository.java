package ru.itis.shop.repositories;

import ru.itis.shop.models.Message;

import java.util.List;

public interface MessagesRepository{
    List<Message> findAll();
    List<Message> findForPerson(Long id);
    void save(Message message);
}
