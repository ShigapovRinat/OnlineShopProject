package ru.itis.onlineShop.repositories;

import ru.itis.onlineShop.models.Message;

import java.util.List;

public interface MessagesRepository{
    List<Message> findAll();
    List<Message> findForPerson(Long id);
    void save(Message message);
}
