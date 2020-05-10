package ru.itis.shop.repositories;

import ru.itis.shop.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<String, User> {
    Optional<User> findByConfirmLink(String confirmLink);
    Optional<User> findById(Long id);
    void confirmed(String email);
    void deleteUserById(Long id);
    void updatePassword(String email, String password);
    void updateName(String email, String name);
}
