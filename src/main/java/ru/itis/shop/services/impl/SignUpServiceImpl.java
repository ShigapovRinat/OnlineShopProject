package ru.itis.shop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.shop.dto.SignUpDto;
import ru.itis.shop.models.User;
import ru.itis.shop.models.UserRole;
import ru.itis.shop.repositories.UsersRepository;
import ru.itis.shop.services.SignUpService;


@Component
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    @Qualifier(value = "usersRepositoryEntityManagerImpl")
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpDto signUpDto, String confirmLink) {
        if (!usersRepository.find(signUpDto.getEmail()).isPresent()) {
            User user = User.builder()
                    .name(signUpDto.getName())
                    .email(signUpDto.getEmail())
                    .hashPassword(passwordEncoder.encode(signUpDto.getPassword()))
                    .isConfirmed(false)
                    .role(UserRole.USER)
                    .confirmLink(confirmLink)
                    .build();
            usersRepository.save(user);
        } else throw new IllegalArgumentException("Пользователь с таким email уже существует");
    }
}
