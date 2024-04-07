package com.naeun2934.acshop.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class SellerRepositoryImpl implements SellerRepository {
    private final EntityManager em;

    @Autowired
    public SellerRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void save(Seller seller) {
        if (seller.getId() == null) {
            em.persist(seller);
        } else {
            em.merge(seller);
        }
    }

}
