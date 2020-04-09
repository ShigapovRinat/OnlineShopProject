package ru.itis.onlineShop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.onlineShop.models.Person;
import ru.itis.onlineShop.repositories.PersonsRepository;
import ru.itis.onlineShop.services.ConfirmService;

import java.util.Optional;

@Component
public class ConfirmServiceImpl implements ConfirmService {

    @Autowired
    @Qualifier(value = "personsRepositoryEntityManagerImpl")
    private PersonsRepository personsRepository;

    @Override
    public void isConfirmed(String confirmLink) {
        Optional<Person> personOptional = personsRepository.findByConfirmLink(confirmLink);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            person.setConfirmed(true);
            personsRepository.confirmed(person.getEmail());
        }
    }

}
