package com.naeun2934.acshop.seller;

import org.springframework.transaction.annotation.Transactional;

public interface SellerRepository {

    @Transactional
    void save(Seller seller);
}
