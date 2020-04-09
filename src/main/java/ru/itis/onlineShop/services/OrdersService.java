package ru.itis.onlineShop.services;

import ru.itis.onlineShop.dto.OrderDto;

import java.util.List;

public interface OrdersService {

    List<OrderDto> getOrdersPerson(String email);

    List<OrderDto> getAllOrders();

    void addOrder(OrderDto orderDto);

}
