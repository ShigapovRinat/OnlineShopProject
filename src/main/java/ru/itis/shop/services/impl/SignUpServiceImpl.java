package ru.itis.shop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.shop.dto.SignUpDto;
import ru.itis.shop.models.Person;
import ru.itis.shop.models.PersonRole;
import ru.itis.shop.repositories.PersonsRepository;
import ru.itis.shop.services.SignUpService;

import java.util.UUID;

@Component
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    @Qualifier(value = "personsRepositoryEntityManagerImpl")
    private PersonsRepository personsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpDto signUpDto) {

        if (!personsRepository.find(signUpDto.getEmail()).isPresent()) {
            Person person = Person.builder()
                    .name(signUpDto.getName())
                    .email(signUpDto.getEmail())
                    .hashPassword(passwordEncoder.encode(signUpDto.getPassword()))
                    .isConfirmed(false)
                    .role(PersonRole.USER)
                    .build();
            String confirmLink = UUID.randomUUID().toString();
            person.setConfirmLink(confirmLink);
            personsRepository.save(person);
        } else throw new IllegalArgumentException("Пользователь с таким email уже существует");
    }
}
