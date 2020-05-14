package ru.itis.shop.services;


import ru.itis.shop.dto.SignUpDto;

public interface SignUpService {
    void signUp(SignUpDto signUpDto, String confirmLink);
}
