package ru.itis.onlineShop.services;

import ru.itis.onlineShop.models.Mail;

public interface EmailService {
    void sendMessage(Mail mail, String viewName);
}
