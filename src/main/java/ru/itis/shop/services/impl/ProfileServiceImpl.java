package ru.itis.shop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.shop.dto.NameDto;
import ru.itis.shop.dto.PasswordDto;
import ru.itis.shop.repositories.UsersRepository;
import ru.itis.shop.services.ProfileService;

@Component
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void changeName(NameDto nameDto) {
        usersRepository.updateName(nameDto.getEmail(), nameDto.getName());
    }

    @Override
    public void changePassword(PasswordDto passwordDto) {
        usersRepository.updatePassword(passwordDto.getEmail(), passwordDto.getPassword());
    }
}
