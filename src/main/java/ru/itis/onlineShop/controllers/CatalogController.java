package ru.itis.onlineShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.onlineShop.dto.BasketDto;
import ru.itis.onlineShop.dto.GoodDto;
import ru.itis.onlineShop.services.BasketService;
import ru.itis.onlineShop.services.GoodsService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private GoodsService goodsService;

    @PreAuthorize("permitAll()")
    @GetMapping
    public ModelAndView openCatalogPage(Authentication authentication){
        Map<String, Object> models = new HashMap<>();
        models.put("goods", goodsService.getAllGoods());
        models.put("auth", authentication);
        return new ModelAndView("catalog", models);
    }

}
