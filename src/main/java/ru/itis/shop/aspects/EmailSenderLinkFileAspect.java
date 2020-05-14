package ru.itis.shop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.shop.dto.OrderDto;
import ru.itis.shop.dto.SignUpDto;
import ru.itis.shop.models.Mail;
import ru.itis.shop.models.User;
import ru.itis.shop.services.EmailService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class EmailSenderLinkFileAspect {

   @Autowired
   private EmailService emailService;

    @AfterReturning(value = "execution(* ru.itis.shop.services.SignUpService.signUp())")
    public void sendConfirmation(JoinPoint joinPoint) {
        SignUpDto user = (SignUpDto) joinPoint.getArgs()[0];
        String email = user.getEmail();
        String link = (String) joinPoint.getArgs()[1];

        if (email == null) {
            throw new IllegalArgumentException("email is null");
        }

        Map<String, Object> model = new HashMap<>();
        model.put("link", link);

        Mail mail = Mail.builder()
                .to(email)
                .from("no-reply@gmail.com")
                .subject("Confirm email")
                .model(model)
                .build();

        emailService.sendMessage(mail, "confirm_email.ftl");
    }

    @AfterReturning(value = "execution(* ru.itis.shop.services.OrdersService.addOrder(*))")
    public void sendCreateOrder(JoinPoint joinPoint) {
        OrderDto order = (OrderDto) joinPoint.getArgs()[0];
        String email = order.getEmail();
        order.setCreateAt(LocalDateTime.now());
        if (email == null) {
            throw new IllegalArgumentException("email is null");
        }

        Map<String, Object> model = new HashMap<>();
        model.put("order", order);

        Mail mail = Mail.builder()
                .to(email)
                .from("no-reply@gmail.com")
                .subject("Create order")
                .model(model)
                .build();

        emailService.sendMessage(mail, "create_order_email.ftl");
    }


}