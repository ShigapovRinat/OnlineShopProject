package ru.itis.shop.repositories;

import ru.itis.shop.models.Person;

import java.util.Optional;

public interface PersonsRepository extends CrudRepository<String, Person> {
    Optional<Person> findByConfirmLink(String confirmLink);
    Optional<Person> findById(Long id);
    void confirmed(String email);
    void deletePersonById(Long id);
}
