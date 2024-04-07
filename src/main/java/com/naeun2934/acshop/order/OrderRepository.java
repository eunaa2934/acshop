package com.naeun2934.acshop.order;

import com.naeun2934.acshop.user.User;

import java.util.List;

public interface OrderRepository {
    void save(Order order);

    Order findOne(Long id);

    List<Order> findAllByUser(User user);
}
