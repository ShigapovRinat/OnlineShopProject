package ru.itis.onlineShop.repositories;

import ru.itis.onlineShop.models.Basket;

import java.util.Optional;

public interface BasketRepository extends CrudRepository<Long, Basket> {

    void deleteByPersonIdAndGoodId(Long idPerson, Long idGood);
    Optional<Basket> findByPersonIdAndGoodId(Long idPerson, Long idGood);
    void  updateQuantity(Long id, Integer quantity);
    void deletePersonAll(Long idPerson);
}
