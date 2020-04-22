package ru.itis.shop.services;

import ru.itis.shop.dto.GoodDto;

import java.util.List;

public interface GoodsService {

    List<GoodDto> getAllGoods();
    GoodDto getGood(Long id);
    void addGood(GoodDto goodDto);
    void deleteGood(Long id);


}
