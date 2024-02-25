package com.naeun2934.acshop.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final EntityManager em;

    @Autowired
    public ProductRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Product product) {
        if (product.getId() == null) {
            em.persist(product);
        } else {
            em.merge(product);
        }
    }

    @Override
    public void save(ProductOption productOption) {
        if (productOption.getId() == null) {
            em.persist(productOption);
        } else {
            em.merge(productOption);
        }
    }

    @Override
    public Product findOne(Long productId) {
        return em.find(Product.class, productId);
    }

    @Override
    public List<Product> findAll(String orderBy) {
        String queryString = "SELECT i FROM Product i";

        if (orderBy != null && !orderBy.isEmpty()) {
            queryString += " ORDER BY i." + orderBy + " desc";
        }

        return em.createQuery(queryString, Product.class).getResultList();
    }
}
