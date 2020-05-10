package ru.itis.shop.repositories.entityManagerRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.shop.models.Good;
import ru.itis.shop.models.GoodType;
import ru.itis.shop.repositories.GoodsRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Component
public class GoodsRepositoryEntityManagerImpl implements GoodsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Optional<Good> find(Long id) {
        Good good = entityManager.createQuery("SELECT g FROM Good g WHERE g.email = ?1", Good.class)
                .setParameter(1, id).getResultList()
                .stream().findFirst().orElse(null);
        if (good != null) {
            entityManager.detach(good);
        }
        return Optional.ofNullable(good);
    }

    @Transactional
    @Override
    public List<Good> findAll() {
        return entityManager.createQuery("SELECT g FROM Good g", Good.class).getResultList();
    }

    @Transactional
    @Override
    public void save(Good good) {
        entityManager.persist(good);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        entityManager.createQuery("DELETE FROM Good g WHERE g.id = ?1")
                .setParameter(1, id).executeUpdate();
    }

    @Override
    public List<Good> findByManufacturerOrType(String manufacturer, GoodType type) {
        return entityManager.createQuery("SELECT g FROM Good g WHERE g.manufacturer = ?1 OR g.type = ?2", Good.class)
                .setParameter(1, manufacturer)
                .setParameter(2, type)
                .getResultList();
    }
}
