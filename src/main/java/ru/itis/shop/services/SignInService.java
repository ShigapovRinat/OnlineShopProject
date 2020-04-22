package ru.itis.shop.services;

import ru.itis.shop.dto.SignInDto;
import ru.itis.shop.dto.TokenDto;

public interface SignInService {
    TokenDto signIn(SignInDto signInDto);
}
