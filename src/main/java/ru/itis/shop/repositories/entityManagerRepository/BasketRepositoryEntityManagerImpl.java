package ru.itis.shop.repositories.entityManagerRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.shop.models.Basket;
import ru.itis.shop.models.Good;
import ru.itis.shop.models.Person;
import ru.itis.shop.repositories.BasketRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class BasketRepositoryEntityManagerImpl implements BasketRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void deleteByPersonIdAndGoodId(Long idPerson, Long idGood) {
        entityManager.createQuery("DELETE FROM Basket b WHERE b.person = ?1 AND b.good =?2")
                .setParameter(1, Person.builder().id(idPerson).build())
                .setParameter(2, Good.builder().id(idGood).build())
                .executeUpdate();
    }

    @Transactional
    @Override
    public Optional<Basket> findByPersonIdAndGoodId(Long idPerson, Long idGood) {
        Basket basket = entityManager.createQuery
                ("SELECT b FROM Basket b WHERE b.person = ?1 AND b.good =?2", Basket.class)
                .setParameter(1, Person.builder().id(idPerson).build())
                .setParameter(2, Good.builder().id(idGood).build())
                .getResultList()
                .stream().findFirst().orElse(null);
        if (basket != null){
            entityManager.detach(basket);
        }
        return Optional.ofNullable(basket);
    }

    @Transactional
    @Override
    public void updateQuantity(Long id, Integer quantity) {
        entityManager.createQuery("UPDATE Basket b SET b.quantityGood = ?1 WHERE b.id = ?2")
                .setParameter(1, quantity)
                .setParameter(2, id)
                .executeUpdate();
    }

    @Transactional
    @Override
    public void deletePersonAll(Long idPerson) {
        entityManager.createQuery("DELETE FROM Basket b WHERE b.person = ?1")
                .setParameter(1, Person.builder().id(idPerson).build())
                .executeUpdate();
    }

    @Transactional
    @Override
    public Optional<Basket> find(Long id) {
        return Optional.ofNullable(entityManager.find(Basket.class, id));
    }

    @Transactional
    @Override
    public List<Basket> findAll() {
        return null;
    }

    @Transactional
    @Override
    public void save(Basket basket) {
        entityManager.persist(basket);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        entityManager.createQuery("DELETE FROM Basket b WHERE b.id = ?1")
                .setParameter(1, id).executeUpdate();
    }
}
