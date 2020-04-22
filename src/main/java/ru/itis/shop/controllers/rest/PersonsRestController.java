package ru.itis.shop.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.shop.dto.PersonDto;
import ru.itis.shop.services.PersonsService;

import java.util.List;

@Profile("rest")
@RestController
public class PersonsRestController {

    @Autowired
    private PersonsService personsService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/persons")
    public ResponseEntity<List<PersonDto>> getPersons(){
        return ResponseEntity.ok(personsService.findAllPersons());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deletePerson/{person-id}")
    public ResponseEntity<?> deletePerson(@PathVariable("person-id") Long id) {
        personsService.deletePersonById(id);
        return ResponseEntity.accepted().build();
    }

}
