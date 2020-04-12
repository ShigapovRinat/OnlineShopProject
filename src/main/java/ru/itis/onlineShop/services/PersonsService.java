package ru.itis.onlineShop.services;

import ru.itis.onlineShop.dto.PersonDto;

import java.util.List;


public interface PersonsService {

    List<PersonDto> findAllPersons();
    PersonDto findPerson(String email);
    void addPerson(PersonDto personDto);
    void deletePerson(String email);
    PersonDto findPersonById(Long id);

}
