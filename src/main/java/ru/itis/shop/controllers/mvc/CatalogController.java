package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.shop.services.GoodsService;

import java.util.HashMap;
import java.util.Map;

@Profile("mvc")
@Controller
public class CatalogController {

    @Autowired
    private GoodsService goodsService;

    @PreAuthorize("permitAll()")
    @GetMapping("/catalog")
    public ModelAndView openCatalogPage(Authentication authentication){
        Map<String, Object> models = new HashMap<>();
        models.put("goods", goodsService.getAllGoods());
        models.put("auth", authentication);
        return new ModelAndView("catalog", models);
    }

}
