package ru.itis.onlineShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.onlineShop.dto.GoodDto;
import ru.itis.onlineShop.models.Good;
import ru.itis.onlineShop.models.GoodType;
import ru.itis.onlineShop.services.GoodsService;

@Controller
public class AdminGoodsController {

    @Autowired
    private GoodsService goodsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/deleteGood/{good-id}")
    public String deleteGood(@PathVariable("good-id") Long goodId) {
        goodsService.deleteGood(goodId);
        return "redirect:/catalog";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addGood")
    public ModelAndView openAddGoodPage() {
        return new ModelAndView("add_good");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addGood")
    public ModelAndView addGood(GoodDto goodDto, @RequestParam String type) {
        try {
            goodDto.setType(GoodType.valueOf(type));
            System.out.println(goodDto.toString());
            if (goodDto.getTitle().equals("") || goodDto.getDescription().equals("")
                    || goodDto.getPrice() == null || goodDto.getType() == null)
                throw new IllegalArgumentException("Заполните все параметры");
            goodsService.addGood(goodDto);
            return new ModelAndView("add_good", "message", "Успешно добавлено");
        } catch (IllegalArgumentException e) {
            return new ModelAndView("add_good", "message", e.getMessage());
        }
    }
}
