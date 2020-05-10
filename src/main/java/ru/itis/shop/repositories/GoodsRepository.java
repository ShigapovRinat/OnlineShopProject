package ru.itis.shop.repositories;

import ru.itis.shop.models.Good;
import ru.itis.shop.models.GoodType;

import java.util.List;

public interface GoodsRepository extends CrudRepository<Long, Good> {
    List<Good> findByManufacturerOrType(String manufacturer, GoodType type);
}
