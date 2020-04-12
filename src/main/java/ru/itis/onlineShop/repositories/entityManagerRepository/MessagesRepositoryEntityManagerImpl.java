package ru.itis.onlineShop.repositories.entityManagerRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.onlineShop.models.Message;
import ru.itis.onlineShop.repositories.MessagesRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Transactional
@Component
public class MessagesRepositoryEntityManagerImpl implements MessagesRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Message> findAll() {
        return entityManager.createQuery("SELECT m FROM Message m", Message.class).getResultList();
    }

    @Transactional
    @Override
    public List<Message> findForPerson(Long idUser) {
        return entityManager.createQuery("SELECT m FROM Message m WHERE m.fromId = ?1 OR m.whomId = ?1", Message.class)
                .setParameter(1, idUser)
                .getResultList();
    }

    @Transactional
    @Override
    public void save(Message message) {
        entityManager.persist(message);
    }

}
