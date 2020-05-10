package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.shop.dto.BasketDto;
import ru.itis.shop.dto.GoodDto;
import ru.itis.shop.services.BasketService;
import ru.itis.shop.services.GoodsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("mvc")
@Controller
public class BasketController {

    @Autowired
    private BasketService basketService;

    @Autowired
    private GoodsService goodsService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/basket")
    public ModelAndView openBasketPage(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<BasketDto> basket =  basketService.getBasket(userDetails.getUsername());
        if (basket.isEmpty()){
            return new ModelAndView("basket");
        }
        GoodDto goodDto = basket.get(0).getGoodDto();
        Map<String, Object> map = new HashMap<>();
        map.put("basket", basket);
        map.put("recommendations",  goodsService.getRecommendations(goodDto.getManufacturer(), goodDto.getType()));
        return new ModelAndView("basket", map);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/catalog")
    public ModelAndView addGoodBasket(Authentication authentication, @RequestParam Long id,
                                      @RequestParam(defaultValue = "1") Integer quantity){
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

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/basket")
    public String deleteGoodFromBasket(Authentication authentication, BasketDto basketDto){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        basketService.deleteBasket(userDetails.getUsername(), basketDto);
        return "redirect:/basket";
    }


}
