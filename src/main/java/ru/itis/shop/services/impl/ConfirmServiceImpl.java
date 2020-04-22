package ru.itis.shop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.itis.shop.models.Person;
import ru.itis.shop.repositories.PersonsRepository;
import ru.itis.shop.services.ConfirmService;

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
