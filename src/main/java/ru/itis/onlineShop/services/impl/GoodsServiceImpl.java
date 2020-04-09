package ru.itis.onlineShop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.onlineShop.dto.GoodDto;
import ru.itis.onlineShop.models.Good;
import ru.itis.onlineShop.repositories.GoodsRepository;
import ru.itis.onlineShop.services.GoodsService;

import java.util.List;
import java.util.Optional;

@Component
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public List<GoodDto> getAllGoods() {
        return GoodDto.from(goodsRepository.findAll());
    }

    @Override
    public GoodDto getGood(Long id) {
        Optional<Good> goodOptional = goodsRepository.find(id);
        if (goodOptional.isPresent()){
            return GoodDto.from(goodOptional.get());
        } else throw new IllegalArgumentException("Good did not find");
    }

    @Override
    public void addGood(GoodDto goodDto) {
        Good good = Good.builder()
                .title(goodDto.getTitle())
                .description(goodDto.getDescription())
                .price(goodDto.getPrice())
                .type(goodDto.getType())
                .build();
        goodsRepository.save(good);
    }

    @Override
    public void deleteGood(Long id) {
        goodsRepository.delete(id);
    }
}
