package ru.itis.shop.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.shop.models.Mail;
import ru.itis.shop.models.User;
import ru.itis.shop.services.EmailService;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class EmailSenderLinkFileAspect {

   @Autowired
   private EmailService emailService;

    @AfterReturning(value = "execution(* ru.itis.shop.repositories.UsersRepository.save(*))")
    public void sendConfirmation(JoinPoint joinPoint) {
        User user = (User) joinPoint.getArgs()[0];
        String email = user.getEmail();
        String link = user.getConfirmLink();

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
}