package ru.itis.shop.services;

import ru.itis.shop.dto.UserDto;

import java.util.List;


public interface UsersService {

    List<UserDto> findAllUsers();
    UserDto findPerson(String email);
    void addUser(UserDto userDto);
    void deleteUserByEmail(String email);
    void deleteUserById(Long id);
    UserDto findPersonById(Long id);

}
