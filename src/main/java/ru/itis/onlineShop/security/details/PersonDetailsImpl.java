package ru.itis.onlineShop.security.details;


import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.onlineShop.models.Person;

import java.util.Collection;
import java.util.Collections;

@Builder
@Data
public class PersonDetailsImpl implements UserDetails {

    private Person person;

    public PersonDetailsImpl(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(person.getRole().name());
        return Collections.singletonList(authority);

    }

    @Override
    public String getPassword() {
        return person.getHashPassword();
    }

    @Override
    public String getUsername() {
        return person.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return person.isConfirmed();
    }

    public Person getPerson() {
        return person;
    }
}
