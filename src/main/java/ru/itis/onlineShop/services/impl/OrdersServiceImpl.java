package ru.itis.onlineShop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.onlineShop.dto.BasketDto;
import ru.itis.onlineShop.dto.GoodDto;
import ru.itis.onlineShop.dto.OrderDto;
import ru.itis.onlineShop.models.Good;
import ru.itis.onlineShop.models.Order;
import ru.itis.onlineShop.models.Person;
import ru.itis.onlineShop.repositories.OrdersRepository;
import ru.itis.onlineShop.repositories.PersonsRepository;
import ru.itis.onlineShop.services.OrdersService;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private PersonsRepository personsRepository;

    @Override
    public List<OrderDto> getAllOrders() {
        List<OrderDto> orderDtoList = new ArrayList<>();
        Map<String, List<BasketDto>> map = new HashMap<>();
        LocalDateTime createAt = null;
        String id = "start";
        for (Order order : ordersRepository.findAll()) {
            if (!map.containsKey(order.getOrderId())) {
                if (!id.equals("start")) {
                    orderDtoList.add(OrderDto.builder()
                            .email(order.getPerson().getEmail())
                            .createAt(createAt)
                            .idOrder(id)
                            .goodsBasket(map.get(id))
                            .build());
                }
                id = order.getOrderId();
                createAt = order.getCreateAt();
                map.put(id, new ArrayList<>());
            }
            map.get(id)
                    .add(BasketDto.builder()
                            .quantityGood(order.getQuantityGood())
                            .email(order.getPerson().getEmail())
                            .goodDto(GoodDto.from(order.getGood()))
                            .build()
                    );
        }
        return orderDtoList;
    }

    @Override
    public List<OrderDto> getOrdersPerson(String email) {
        Optional<Person> optionalPerson = personsRepository.find(email);
        if (optionalPerson.isPresent()) {
            Set<Order> orders = optionalPerson.get().getOrders();
            List<OrderDto> orderDtoList = new ArrayList<>();
            List<BasketDto> basketDtoList = new ArrayList<>();
            String orderId = "start";
            LocalDateTime createAt = null;
            Iterator<Order> iterator = orders.iterator();
            while (iterator.hasNext()) {
                Order order = iterator.next();
                if (!orderId.equals(order.getOrderId())) {
                    if (!orderId.equals("start")) {
                        orderDtoList.add(OrderDto.builder()
                                .idOrder(orderId)
                                .createAt(createAt)
                                .goodsBasket(basketDtoList).build());
                    }
                    orderId = order.getOrderId();
                    createAt = order.getCreateAt();
                    basketDtoList = new ArrayList<>();
                }

                basketDtoList.add(BasketDto.builder()
                        .quantityGood(order.getQuantityGood())
                        .goodDto(GoodDto.from(order.getGood()))
                        .build()
                );

                if (!iterator.hasNext()) {
                    orderDtoList.add(OrderDto.builder()
                            .idOrder(orderId)
                            .createAt(createAt)
                            .goodsBasket(basketDtoList).build());
                }
            }
            return orderDtoList;
        } else throw new IllegalStateException("Пользователь не найден");
    }

    @Override
    public void addOrder(OrderDto orderDto) {
        LocalDateTime createAt = LocalDateTime.now();
        String orderId = UUID.randomUUID().toString();
        Optional<Person> optionalPerson = personsRepository.find(orderDto.getEmail());
        if (optionalPerson.isPresent()) {
            for (BasketDto basketDto : orderDto.getGoodsBasket()) {
                ordersRepository.save(Order.builder()
                        .orderId(orderId)
                        .person(Person.builder()
                                .id(optionalPerson.get().getId())
                                .build())
                        .good(Good.builder()
                                .id(basketDto.getGoodDto().getId())
                                .build())
                        .quantityGood(basketDto.getQuantityGood())
                        .createAt(createAt)
                        .build());
            }
        } else throw new IllegalStateException("Пользователь не найден");
    }
}
