package ru.itis.onlineShop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.onlineShop.models.Person;
import ru.itis.onlineShop.models.PersonRole;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private Long id;
    private String email;
    private String name;
    private String hashPassword;
    private boolean isConfirmed;
    private PersonRole role;

    public static PersonDto from(Person person){
        return PersonDto.builder()
                .id(person.getId())
                .email(person.getEmail())
                .name(person.getName())
                .isConfirmed(person.isConfirmed())
                .role(person.getRole())
                .build();
    }

    public static List<PersonDto> from(List<Person> persons){
        return persons.stream()
                .map(PersonDto::from)
                .collect(Collectors.toList());
    }
}
