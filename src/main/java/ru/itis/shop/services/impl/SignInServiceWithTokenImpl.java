package ru.itis.shop.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.shop.dto.SignInDto;
import ru.itis.shop.dto.TokenDto;
import ru.itis.shop.models.Person;
import ru.itis.shop.repositories.PersonsRepository;
import ru.itis.shop.services.SignInService;

import java.util.Optional;

@Profile("rest")
@Component
public class SignInServiceWithTokenImpl implements SignInService {

    @Value("${jwt.secret}")
    private String secret;


    @Autowired
    private PersonsRepository personsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public TokenDto signIn(SignInDto signInDto) {
        Optional<Person> personOptional = personsRepository.find(signInDto.getEmail());
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            if (passwordEncoder.matches(signInDto.getPassword(), person.getHashPassword())) {
                String token = Jwts.builder()
                        .setSubject(person.getId().toString())
                        .claim("email", person.getEmail())
                        .claim("role", person.getRole().name())
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
                return new TokenDto(token);
            } else throw new AccessDeniedException("Wrong email/password");
        } else throw new AccessDeniedException("Person not found");
    }

}

