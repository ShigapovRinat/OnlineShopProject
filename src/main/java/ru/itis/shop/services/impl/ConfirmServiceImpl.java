package ru.itis.shop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.shop.models.User;
import ru.itis.shop.repositories.UsersRepository;
import ru.itis.shop.services.ConfirmService;

import java.util.Optional;

@Component
public class ConfirmServiceImpl implements ConfirmService {

    @Autowired
    @Qualifier(value = "usersRepositoryEntityManagerImpl")
    private UsersRepository usersRepository;

    @Override
    public void isConfirmed(String confirmLink) {
        Optional<User> personOptional = usersRepository.findByConfirmLink(confirmLink);
        if (personOptional.isPresent()) {
            User user = personOptional.get();
            user.setConfirmed(true);
            usersRepository.confirmed(user.getEmail());
        }
    }

}
