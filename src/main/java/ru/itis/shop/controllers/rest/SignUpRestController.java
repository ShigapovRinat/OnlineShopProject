package ru.itis.shop.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.itis.shop.dto.SignUpDto;
import ru.itis.shop.services.SignUpService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Profile("rest")
@RestController
public class SignUpRestController {

    @Autowired
    private SignUpService service;

    @Autowired
    private MessageSource messageSource;

    @PostMapping(value = "/signUp", produces = "text/plain;charset=UTF-8")
    public ResponseEntity registration(@Valid @RequestBody SignUpDto dto, Locale locale) {
        try {
            if (dto.getEmail().equals("") || dto.getPassword().equals("")
                    || dto.getName().equals(""))
                return ResponseEntity.badRequest().body(messageSource.getMessage("rest.page.not.all.parameters", null, locale));

            service.signUp(dto, UUID.randomUUID().toString());
            return ResponseEntity.ok(messageSource.getMessage("rest.success", null, locale));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(messageSource.getMessage("rest.page.registration.email.has", null, locale));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
