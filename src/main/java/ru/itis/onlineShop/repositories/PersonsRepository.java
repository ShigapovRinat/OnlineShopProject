package ru.itis.onlineShop.repositories;

import ru.itis.onlineShop.models.Person;

import java.util.Optional;

public interface PersonsRepository extends CrudRepository<String, Person> {
    Optional<Person> findByConfirmLink(String confirmLink);
    void confirmed(String email);
}
