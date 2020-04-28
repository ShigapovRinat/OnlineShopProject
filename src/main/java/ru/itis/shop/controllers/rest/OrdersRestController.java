package ru.itis.shop.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.shop.dto.BasketDto;
import ru.itis.shop.dto.OrderDto;
import ru.itis.shop.security.jwt.details.UserDetailsForTokenImpl;
import ru.itis.shop.services.BasketService;
import ru.itis.shop.services.OrdersService;

import java.util.List;

@Profile("rest")
@RestController
public class OrdersRestController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private BasketService basketService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsForTokenImpl userDetails = (UserDetailsForTokenImpl) authentication.getPrincipal();
        return ResponseEntity.ok(ordersService.getOrdersPerson(userDetails.getUsername()));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsForTokenImpl userDetails = (UserDetailsForTokenImpl) authentication.getPrincipal();
        List<BasketDto> basketDtoList = basketService.getBasket(userDetails.getUsername());
        ordersService.addOrder(OrderDto.builder()
                .goodsBasket(basketDtoList)
                .email(userDetails.getUsername())
                .build()
        );
        basketService.deleteAllBasket(userDetails.getUsername());
        return ResponseEntity.ok(basketDtoList);
    }
}