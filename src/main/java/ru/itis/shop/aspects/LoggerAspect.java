package ru.itis.shop.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import ru.itis.shop.dto.SignUpDto;

@Aspect
@Component
public class LoggerAspect {

    private static Logger log = Logger.getLogger(LoggerAspect.class);

    @Around(value = "execution(* ru.itis.shop.services.SignUpService.signUp(*))")
    public void signUpLogger(ProceedingJoinPoint joinPoint){
        SignUpDto signUpDto = (SignUpDto) joinPoint.getArgs()[0];
        log.info("User " + signUpDto.getEmail() + " try to registration");
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new IllegalArgumentException();
        }
        log.info("User " + signUpDto.getEmail() + " success registration");
    }

    @Before(value = "execution (* ru.itis.shop.services.ConfirmService.isConfirmed(*))")
    public void signInLogger(JoinPoint joinPoint) {
        String link = (String) joinPoint.getArgs()[0];
        log.info("User with link " + link + " confirmed email");
    }
}