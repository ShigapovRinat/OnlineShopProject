package ru.itis.shop.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.shop.dto.GoodDto;
import ru.itis.shop.services.GoodsService;

import java.util.List;

@Profile("rest")
@RestController
public class CatalogRestController {

    @Autowired
    private GoodsService goodsService;

    @PreAuthorize("permitAll()")
    @GetMapping("/catalog")
    public ResponseEntity<List<GoodDto>> openCatalogPage(){
        return ResponseEntity.accepted().build();
    }

}
