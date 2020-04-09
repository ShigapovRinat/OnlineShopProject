package ru.itis.onlineShop.repositories.entityManagerRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.onlineShop.models.Good;
import ru.itis.onlineShop.models.Order;
import ru.itis.onlineShop.models.Person;
import ru.itis.onlineShop.repositories.OrdersRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class OrdersRepositoryEntityManagerImpl implements OrdersRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void deleteByPersonIdAndGoodId(Long idPerson, Long idGood) {
        entityManager.createQuery("DELETE FROM ru.itis.onlineShop.models.Order o WHERE o.person = ?1 AND o.good =?2")
                .setParameter(1, Person.builder().id(idPerson).build())
                .setParameter(2, Good.builder().id(idGood).build())
                .executeUpdate();
    }

    @Transactional
    @Override
    public Optional<Order> findByPersonIdAndGoodId(Long idPerson, Long idGood) {
        Order order = entityManager.createQuery
                ("SELECT o FROM ru.itis.onlineShop.models.Order o WHERE o.person = ?1 AND o.good =?2", Order.class)
                .setParameter(1, Person.builder().id(idPerson).build())
                .setParameter(2, Good.builder().id(idGood).build())
                .getResultList()
                .stream().findFirst().orElse(null);
        if (order != null) {
            entityManager.detach(order);
        }
        return Optional.ofNullable(order);
    }

    @Transactional
    @Override
    public Optional<Order> find(Long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Transactional
    @Override
    public List<Order> findAll() {
        return entityManager.createQuery("SELECT o FROM ru.itis.onlineShop.models.Order o", Order.class).getResultList();
    }

    @Transactional
    @Override
    public void save(Order order) {
        entityManager.persist(order);
    }

    @Transactional
    @Override
    public void delete(Long orderId) {
        entityManager.createQuery("DELETE FROM ru.itis.onlineShop.models.Order o WHERE o.orderId = ?1")
                .setParameter(1, orderId).executeUpdate();
    }
}
