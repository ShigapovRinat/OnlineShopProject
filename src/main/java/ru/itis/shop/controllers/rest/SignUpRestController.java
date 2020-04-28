package ru.itis.shop.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.shop.dto.SignUpDto;
import ru.itis.shop.services.SignUpService;

@Profile("rest")
@RestController
public class SignUpRestController {

    @Autowired
    private SignUpService service;

    @PostMapping("/signUp")
    public ResponseEntity registration(@RequestBody SignUpDto dto) {
        try {
            if (dto.getEmail().equals("") || dto.getPassword().equals("")
                    || dto.getName().equals("")) throw new IllegalArgumentException("Заполните все параметры");
            service.signUp(dto);
            return ResponseEntity.accepted().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
