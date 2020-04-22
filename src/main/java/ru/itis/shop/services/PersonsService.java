package ru.itis.shop.services;

import ru.itis.shop.dto.PersonDto;

import java.util.List;


public interface PersonsService {

    List<PersonDto> findAllPersons();
    PersonDto findPerson(String email);
    void addPerson(PersonDto personDto);
    void deletePersonByEmail(String email);
    void deletePersonById(Long id);
    PersonDto findPersonById(Long id);

}
