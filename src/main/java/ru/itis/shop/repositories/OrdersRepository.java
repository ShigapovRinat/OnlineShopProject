package ru.itis.shop.repositories;

import ru.itis.shop.models.Order;

import java.util.Optional;

public interface OrdersRepository extends CrudRepository<Long, Order> {
    void deleteByPersonIdAndGoodId(Long idPerson, Long idGood);
    Optional<Order> findByPersonIdAndGoodId(Long idPerson, Long idGood);
}
