package ru.itis.shop.security.mvc.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.itis.shop.models.User;
import ru.itis.shop.repositories.UsersRepository;

import java.util.Optional;

@Profile("mvc")
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Qualifier(value = "usersRepositoryEntityManagerImpl")
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = usersRepository.find(email);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            return new UserDetailsImpl(user);
        } throw new UsernameNotFoundException("Пользователь не найден");
    }
}
