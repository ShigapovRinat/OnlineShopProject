package ru.itis.onlineShop.repositories.entityManagerRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.onlineShop.models.Good;
import ru.itis.onlineShop.models.Person;
import ru.itis.onlineShop.repositories.PersonsRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class PersonsRepositoryEntityManagerImpl implements PersonsRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @Override
    public Optional<Person> findByConfirmLink(String confirmLink) {
        Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.confirmLink = ?1", Person.class)
                .setParameter(1, confirmLink)
                .getResultList()
                .stream().findFirst().orElse(null);
        if (person != null) {
            entityManager.detach(person);
        }
        return Optional.ofNullable(person);
    }

    @Transactional
    @Override
    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Person.class, id));
    }

    @Transactional
    @Override
    public void confirmed(String email) {
        entityManager.createQuery("UPDATE Person p SET p.isConfirmed = TRUE WHERE p.email = ?1")
                .setParameter(1, email).executeUpdate();
    }

    @Transactional
    @Override
    public Optional<Person> find(String email) {
        Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.email = ?1", Person.class)
                .setParameter(1, email).getResultList()
                .stream().findFirst().orElse(null);
        if (person != null) {
            entityManager.detach(person);
        }
        return Optional.ofNullable(person);
    }

    @Transactional
    @Override
    public List<Person> findAll() {
        return entityManager.createQuery("SELECT person FROM Person person", Person.class).getResultList();
    }

    @Transactional
    @Override
    public void save(Person person) {
        entityManager.persist(person);
    }

    @Transactional
    @Override
    public void delete(String email) {
        entityManager.createQuery("DELETE FROM Person p WHERE p.email = ?1")
                .setParameter(1, email).executeUpdate();
    }
}
