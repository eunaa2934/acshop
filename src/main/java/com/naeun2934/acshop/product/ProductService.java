package com.naeun2934.acshop.product;

import java.util.List;

public interface ProductService {
    void saveProduct(Product product);

    List<Product> findProducts(String orderBy);

    Product findOne(Long productId);
}
