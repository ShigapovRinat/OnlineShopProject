package ru.itis.onlineShop.services;

import ru.itis.onlineShop.dto.BasketDto;

import java.util.List;

public interface BasketService {

    List<BasketDto> getBasket(String email);

    void addBasket(String email, BasketDto basketDto);

    void deleteBasket(String email, BasketDto basketDto);

    void deleteAllBasket(String email);
}
