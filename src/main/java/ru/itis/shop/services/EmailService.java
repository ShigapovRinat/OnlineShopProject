package ru.itis.shop.services;

import ru.itis.shop.models.Mail;

public interface EmailService {
    void sendMessage(Mail mail, String viewName);
}
