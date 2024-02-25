package com.naeun2934.acshop.product;

import java.util.List;

public interface ProductRepository {
    void save(Product product);

    void save(ProductOption productOption);

    Product findOne(Long id);

    List<Product> findAll(String orderBy);
}
