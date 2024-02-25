package com.naeun2934.acshop.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final EntityManager em;

    @Autowired
    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(User user) {

        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    @Override
    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class).getResultList();
    }

    @Override
    public User findByUserEmail(String userEmail) {
        try {
            return em.createQuery("select m from User m where m.userEmail = :userEmail", User.class)
                    .setParameter("userEmail", userEmail).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
