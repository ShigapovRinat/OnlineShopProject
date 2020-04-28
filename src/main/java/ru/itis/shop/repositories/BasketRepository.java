package ru.itis.shop.repositories;

import ru.itis.shop.models.Basket;

import java.util.Optional;

public interface BasketRepository extends CrudRepository<Long, Basket> {

    void deleteByUserIdAndGoodId(Long idUser, Long idGood);
    Optional<Basket> findByUserIdAndGoodId(Long idUser, Long idGood);
    void  updateQuantity(Long id, Integer quantity);
    void deleteUserAll(Long idUser);
}
