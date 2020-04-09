package ru.itis.onlineShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.onlineShop.dto.BasketDto;
import ru.itis.onlineShop.dto.GoodDto;
import ru.itis.onlineShop.services.BasketService;
import ru.itis.onlineShop.services.GoodsService;
import ru.itis.onlineShop.services.PersonsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BasketController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private GoodsService goodsService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/basket")
    public ModelAndView openCatalogPage(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<BasketDto> basket =  basketService.getBasket(userDetails.getUsername());
        if (basket.isEmpty()){
            return new ModelAndView("basket");
        }
        return new ModelAndView("basket", "basket", basket);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/catalog")
    public ModelAndView addGoodBasket(Authentication authentication, @RequestParam Long id,
                                      @RequestParam(defaultValue = "1") Integer quantity){
        System.out.println(quantity);
        try {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            BasketDto basketDto = BasketDto.builder().goodDto(GoodDto.builder().id(id).build()).quantityGood(quantity).build();
            basketService.addBasket(userDetails.getUsername(), basketDto);
            Map<String, Object> models = new HashMap<>();
            models.put("goods", goodsService.getAllGoods());
            models.put("auth", authentication);
            models.put("message", "Товар успешно добавлен в корзину");
            return new ModelAndView("catalog", models);
        } catch (IllegalStateException e){
            Map<String, Object> models = new HashMap<>();
            models.put("goods", goodsService.getAllGoods());
            models.put("auth", authentication);
            models.put("message", e.getMessage());
            return new ModelAndView("catalog", models);
        }
    }


}
