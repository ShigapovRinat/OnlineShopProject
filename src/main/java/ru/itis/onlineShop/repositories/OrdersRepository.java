package ru.itis.onlineShop.repositories;

import ru.itis.onlineShop.models.Order;

import java.util.Optional;

public interface OrdersRepository extends CrudRepository<Long, Order> {
    void deleteByPersonIdAndGoodId(Long idPerson, Long idGood);
    Optional<Order> findByPersonIdAndGoodId(Long idPerson, Long idGood);
}
