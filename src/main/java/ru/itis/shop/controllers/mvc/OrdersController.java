package ru.itis.shop.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.shop.dto.BasketDto;
import ru.itis.shop.dto.OrderDto;
import ru.itis.shop.services.BasketService;
import ru.itis.shop.services.OrdersService;

import java.util.List;

@Profile("mvc")
@Controller
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private BasketService basketService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/orders")
    public ModelAndView openCatalogPage(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<OrderDto> orderDtoList =  ordersService.getOrdersPerson(userDetails.getUsername());
        if (orderDtoList.isEmpty()){
            return new ModelAndView("order");
        }
        return new ModelAndView("order", "orders", orderDtoList);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create_order")
    public ModelAndView createOrder(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<BasketDto> basketDtoList = basketService.getBasket(userDetails.getUsername());
        ordersService.addOrder(OrderDto.builder()
                .goodsBasket(basketDtoList)
                .email(userDetails.getUsername())
                .build()
        );
        basketService.deleteAllBasket(userDetails.getUsername());
        return new ModelAndView("redirect:/orders");
    }
}
