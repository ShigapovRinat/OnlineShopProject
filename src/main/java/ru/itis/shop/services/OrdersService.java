package ru.itis.shop.services;

import ru.itis.shop.dto.OrderDto;

import java.util.List;

public interface OrdersService {

    List<OrderDto> getOrdersPerson(String email);

    List<OrderDto> getAllOrders();

    void addOrder(OrderDto orderDto);

}
