package ru.itis.shop.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.itis.shop.dto.AddBasketForm;
import ru.itis.shop.dto.BasketDto;
import ru.itis.shop.dto.GoodDto;
import ru.itis.shop.security.jwt.details.UserDetailsForTokenImpl;
import ru.itis.shop.services.BasketService;
import ru.itis.shop.services.GoodsService;

import java.util.List;

@Profile("rest")
@RestController
public class BasketRestController {

    @Autowired
    private BasketService basketService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/basket")
    public ResponseEntity<List<BasketDto>> getBasket() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsForTokenImpl userDetails = (UserDetailsForTokenImpl) authentication.getPrincipal();
        return ResponseEntity.ok(basketService.getBasket(userDetails.getUsername()));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/basket/{basket-id}")
    public ResponseEntity<?> deleteGoodFromBasket(@PathVariable("basket-id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsForTokenImpl userDetails = (UserDetailsForTokenImpl) authentication.getPrincipal();
        basketService.deleteBasket(userDetails.getUsername(), BasketDto.builder().id(id).build());
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/catalog")
    public ResponseEntity<?> addGoodBasket(@RequestBody AddBasketForm form){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsForTokenImpl userDetails = (UserDetailsForTokenImpl) authentication.getPrincipal();
            BasketDto basketDto = BasketDto.builder()
                    .goodDto(GoodDto.builder()
                            .id(form.getGoodId()).build())
                    .quantityGood(form.getQuantity())
                    .build();
            System.out.println(basketDto);
            System.out.println(userDetails.getUsername());
            basketService.addBasket(userDetails.getUsername(), basketDto);
            return ResponseEntity.ok("Success added");
        } catch (IllegalStateException e){
            return ResponseEntity.badRequest().body(e);
        }
    }
}
