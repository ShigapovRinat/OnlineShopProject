package ru.itis.onlineShop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.onlineShop.dto.GoodDto;
import ru.itis.onlineShop.dto.PersonDto;
import ru.itis.onlineShop.models.Basket;
import ru.itis.onlineShop.models.Good;
import ru.itis.onlineShop.models.Person;
import ru.itis.onlineShop.repositories.PersonsRepository;
import ru.itis.onlineShop.services.PersonsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PersonsServiceImpl implements PersonsService {

    @Autowired
    private PersonsRepository personsRepository;

    @Override
    public List<PersonDto> findAllPersons() {
        return PersonDto.from(personsRepository.findAll());
    }

    @Override
    public PersonDto findPerson(String email) {
        Optional<Person> personOptional = personsRepository.find(email);
        if (personOptional.isPresent()){
            return PersonDto.from(personOptional.get());
        } else throw new IllegalArgumentException("Пользователь не найден");
    }

    @Override
    public void addPerson(PersonDto personDto) {
        Person person = Person.builder()
                .email(personDto.getEmail())
                .name(personDto.getName())
                .isConfirmed(personDto.isConfirmed())
                .role(personDto.getRole())
                .build();
        personsRepository.save(person);
    }

    @Override
    public void deletePerson(String email) {
        personsRepository.delete(email);
    }
}
