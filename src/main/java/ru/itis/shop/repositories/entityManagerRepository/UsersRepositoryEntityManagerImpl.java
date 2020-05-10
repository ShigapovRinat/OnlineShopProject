package ru.itis.shop.repositories.entityManagerRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.shop.models.User;
import ru.itis.shop.repositories.UsersRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryEntityManagerImpl implements UsersRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @Override
    public Optional<User> findByConfirmLink(String confirmLink) {
        User user = entityManager.createQuery("SELECT p FROM User p WHERE p.confirmLink = ?1", User.class)
                .setParameter(1, confirmLink)
                .getResultList()
                .stream().findFirst().orElse(null);
        if (user != null) {
            entityManager.detach(user);
        }
        return Optional.ofNullable(user);
    }

    @Transactional
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Transactional
    @Override
    public void confirmed(String email) {
        entityManager.createQuery("UPDATE User p SET p.isConfirmed = TRUE WHERE p.email = ?1")
                .setParameter(1, email).executeUpdate();
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Transactional
    @Override
    public void updatePassword(String email, String password) {
        entityManager.createQuery("UPDATE User p SET p.password = ?1 WHERE p.email = ?2")
                .setParameter(1, password)
                .setParameter(1, email)
                .executeUpdate();
    }

    @Transactional
    @Override
    public void updateName(String email, String name) {
        entityManager.createQuery("UPDATE User p SET p.name = ?1 WHERE p.email = ?2")
                .setParameter(1, name)
                .setParameter(2, email)
                .executeUpdate();
    }

    @Transactional
    @Override
    public Optional<User> find(String email) {
        User user = entityManager.createQuery("SELECT p FROM User p WHERE p.email = ?1", User.class)
                .setParameter(1, email).getResultList()
                .stream().findFirst().orElse(null);
        if (user != null) {
            entityManager.detach(user);
        }
        return Optional.ofNullable(user);
    }

    @Transactional
    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    @Transactional
    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Transactional
    @Override
    public void delete(String email) {
        entityManager.createQuery("DELETE FROM User p WHERE p.email = ?1")
                .setParameter(1, email).executeUpdate();
    }
}
