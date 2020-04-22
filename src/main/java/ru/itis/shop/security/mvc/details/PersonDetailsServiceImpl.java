package ru.itis.shop.security.mvc.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.shop.models.Person;
import ru.itis.shop.repositories.PersonsRepository;

import java.util.Optional;

@Profile("mvc")
@Component
public class PersonDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Qualifier(value = "personsRepositoryEntityManagerImpl")
    private PersonsRepository personsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> userOptional = personsRepository.find(email);
        if (userOptional.isPresent()){
            Person person = userOptional.get();
            return new PersonDetailsImpl(person);
        } throw new UsernameNotFoundException("Пользователь не найден");
    }
}
