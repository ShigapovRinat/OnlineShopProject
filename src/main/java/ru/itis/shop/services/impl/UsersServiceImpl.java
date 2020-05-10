package ru.itis.shop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import ru.itis.shop.dto.UserDto;
import ru.itis.shop.models.User;
import ru.itis.shop.repositories.UsersRepository;
import ru.itis.shop.services.UsersService;

import java.util.List;
import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<UserDto> findAllUsers() {
        return UserDto.from(usersRepository.findAll());
    }

    @Override
    public UserDto findPerson(String email) {
        Optional<User> personOptional = usersRepository.find(email);
        if (personOptional.isPresent()){
            return UserDto.from(personOptional.get());
        } else throw new IllegalArgumentException("Пользователь не найден");
    }

    @Override
    public void addUser(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .isConfirmed(userDto.isConfirmed())
                .role(userDto.getRole())
                .build();
        usersRepository.save(user);
    }

    @Override
    public void deleteUserByEmail(String email) {
        usersRepository.delete(email);
    }

    @Override
    public void deleteUserById(Long id) {
        usersRepository.deleteUserById(id);
    }

    @Override
    public UserDto findPersonById(Long id) {
        Optional<User> personOptional = usersRepository.findById(id);
        if (personOptional.isPresent()){
            return UserDto.from(personOptional.get());
        } else throw new IllegalArgumentException("Пользователь не найден");
    }
}
