package ru.itis.shop.services;

import ru.itis.shop.dto.NameDto;
import ru.itis.shop.dto.PasswordDto;

public interface ProfileService {
    void changeName(NameDto nameDto);
    void changePassword(PasswordDto passwordDto);
}
