package com.naeun2934.acshop.order;

import com.naeun2934.acshop.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManager em;

    @Autowired
    public OrderRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void save(Order order) {
        if (order.getId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }
    }

    @Override
    public Order findOne(Long orderId) {
        return em.find(Order.class, orderId);
    }

    @Override
    public List<Order> findAllByUser(User user) {
        return em.createQuery("select o from Order o where o.user = :user")
                .setParameter("user", user)
                .setMaxResults(1000)
                .getResultList();
    }
}
