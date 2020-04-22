package ru.itis.shop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.shop.dto.PersonDto;
import ru.itis.shop.models.Person;
import ru.itis.shop.repositories.PersonsRepository;
import ru.itis.shop.services.PersonsService;

import java.util.List;
import java.util.Optional;

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
    public void deletePersonByEmail(String email) {
        personsRepository.delete(email);
    }

    @Override
    public void deletePersonById(Long id) {
        personsRepository.deletePersonById(id);
    }

    @Override
    public PersonDto findPersonById(Long id) {
        Optional<Person> personOptional = personsRepository.findById(id);
        if (personOptional.isPresent()){
            return PersonDto.from(personOptional.get());
        } else throw new IllegalArgumentException("Пользователь не найден");
    }
}
