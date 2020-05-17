package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.shop.dto.GoodDto;
import ru.itis.shop.models.GoodType;
import ru.itis.shop.services.GoodsService;

@Profile("mvc")
@Controller
public class GoodsController {

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
    public ModelAndView addGood(GoodDto goodDto, @RequestParam String type, @RequestParam("multipartFile") MultipartFile multipartFile) {
        try {
            goodDto.setMultipartFile(multipartFile);
            goodDto.setType(GoodType.valueOf(type));
            if (!multipartFile.getContentType().substring(0,5).equals("image")){
                System.out.println(multipartFile.getContentType().substring(0, '/'));
                throw new IllegalArgumentException("Загрузите картинку");
            }
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
