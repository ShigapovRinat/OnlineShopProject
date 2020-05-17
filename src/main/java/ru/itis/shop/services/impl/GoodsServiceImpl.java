package ru.itis.shop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.shop.dto.GoodDto;
import ru.itis.shop.models.Good;
import ru.itis.shop.models.GoodType;
import ru.itis.shop.repositories.GoodsRepository;
import ru.itis.shop.services.GoodsService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GoodsServiceImpl implements GoodsService {

    @Value("${storage.path}")
    private String path;

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
        String originalName = goodDto.getMultipartFile().getOriginalFilename();
        String storageFileName = UUID.randomUUID().toString() + originalName.substring(originalName.lastIndexOf('.'));
        File storageFile = new File(path + storageFileName);
        try {
            goodDto.getMultipartFile().transferTo(storageFile);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        Good good = Good.builder()
                .title(goodDto.getTitle())
                .description(goodDto.getDescription())
                .price(goodDto.getPrice())
                .type(goodDto.getType())
                .manufacturer(goodDto.getManufacturer())
                .path(storageFileName)
                .build();
        goodsRepository.save(good);
    }

    @Override
    public void deleteGood(Long id) {
        goodsRepository.delete(id);
    }

    @Override
    public List<GoodDto> getRecommendations(String manufacturer, GoodType type) {
        return GoodDto.from(goodsRepository.findByManufacturerOrType(manufacturer, type));
    }
}
