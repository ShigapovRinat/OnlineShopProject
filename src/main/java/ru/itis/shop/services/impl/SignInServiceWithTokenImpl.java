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
import ru.itis.shop.models.User;
import ru.itis.shop.repositories.UsersRepository;
import ru.itis.shop.services.SignInService;

import java.util.Optional;

@Profile("rest")
@Component
public class SignInServiceWithTokenImpl implements SignInService {

    @Value("${jwt.secret}")
    private String secret;


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public TokenDto signIn(SignInDto signInDto) {
        Optional<User> personOptional = usersRepository.find(signInDto.getEmail());
        if (personOptional.isPresent()) {
            User user = personOptional.get();
            if (passwordEncoder.matches(signInDto.getPassword(), user.getHashPassword())) {
                String token = Jwts.builder()
                        .setSubject(user.getId().toString())
                        .claim("email", user.getEmail())
                        .claim("role", user.getRole().name())
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
                return new TokenDto(token);
            } else throw new AccessDeniedException("Wrong email/password");
        } else throw new AccessDeniedException("User not found");
    }

}

