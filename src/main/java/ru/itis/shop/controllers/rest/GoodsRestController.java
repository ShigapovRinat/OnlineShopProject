package ru.itis.shop.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.shop.dto.GoodDto;
import ru.itis.shop.models.GoodType;
import ru.itis.shop.services.GoodsService;

@Profile("rest")
@RestController
public class GoodsRestController {

    @Autowired
    private GoodsService goodsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteGood/{good-id}")
    public ResponseEntity<?> deleteGood(@PathVariable("good-id") Long goodId) {
        goodsService.deleteGood(goodId);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addGood")
    public ResponseEntity<?> addGood(@RequestBody GoodDto goodDto, @RequestParam String type) {
        try {
            goodDto.setType(GoodType.valueOf(type));
            if (goodDto.getTitle().equals("") || goodDto.getDescription().equals("")
                    || goodDto.getPrice() == null || goodDto.getType() == null)
                throw new IllegalArgumentException("Заполните все параметры");
            goodsService.addGood(goodDto);
            return ResponseEntity.accepted().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}