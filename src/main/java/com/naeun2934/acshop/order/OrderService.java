package com.naeun2934.acshop.order;

import com.naeun2934.acshop.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {
    Order makeOrder(Long userId, OrderProduct orderProduct);

    @Transactional
    void placeAnOrder(Order order, User user);

    // 주문 취소
    @Transactional
    void cancelOrder(Long orderId, Long userId);

    List<Order> makeOrderHistory(User user);
}
