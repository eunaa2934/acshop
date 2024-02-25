package com.naeun2934.acshop.order;

import com.naeun2934.acshop.product.Product;
import com.naeun2934.acshop.product.ProductOption;
import com.naeun2934.acshop.product.ProductRepository;
import com.naeun2934.acshop.user.User;
import com.naeun2934.acshop.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order makeOrder(Long userId, OrderProduct orderProduct) {
        User user = userRepository.findOne(userId);
        Product product = productRepository.findOne(orderProduct.getProduct().getId());
        Order order = new Order(user.getUserEmail(), user.getUserName(), user.getAddress(), OrderStatus.PAYMENT_PENDING);
        orderProduct.getOrderProductOptions().removeIf(orderProductOption -> orderProductOption.getOrderOptionQuantity() <= 0);
        orderProduct.setProduct(product);
        order.addOrderProduct(orderProduct);

        return order;
    }

    @Override
    @Transactional
    public void placeAnOrder(Order order, User user) {
        order.setUser(user);
        for (OrderProduct orderProduct : order.getOrderProducts()) {
            orderProduct.setOrder(order);
            Product product = productRepository.findOne(orderProduct.getProduct().getId());
            List<ProductOption> productOptions = new ArrayList<>(product.getProductOptions());
            Iterator<OrderProductOption> orderProductOptionIterator = orderProduct.getOrderProductOptions().iterator();
            while (orderProductOptionIterator.hasNext()) {
                OrderProductOption orderProductOption = orderProductOptionIterator.next();
                orderProductOption.setOrderProduct(orderProduct);
                for (ProductOption productOption : productOptions) {
                    if (orderProductOption.getOrderOptionNum() == productOption.getOptionNum()) {
                        if (orderProductOption.getOrderOptionQuantity() > productOption.getOptionQuantity()) {
                            // 재고 보다 더 많이 주문한 상품이 있는 경우 주문 중지
                            throw new IllegalStateException("exceedAvailableStock");
                        } else if (orderProductOption.getOrderOptionQuantity() == 0) {
                            // 주문 수량이 0인 경우 해당 항목 삭제
                            orderProductOptionIterator.remove();
                        } else {
                            // 기존 재고 감소
                            productOption.removeOptionQuantity(orderProductOption.getOrderOptionQuantity());
                            // 감소한 후 재고 정보 보존
                            productRepository.save(productOption);
                        }
                    }
                }
            }
        }
        // 상품준비중으로 변경
        order.setOrderStatus(OrderStatus.PREPARING_FOR_PRODUCT);
        // 주문 정보 보존
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Long userId, Long orderId) {
        Order order = orderRepository.findOne(orderId);
        Product product = productRepository.findOne(order.getOrderProducts().get(0).getProduct().getId());
        List<ProductOption> productOptions = new ArrayList<>(product.getProductOptions());
        // 유저가 일치하는지 다시 확인
        if (order.getUser().getId().equals(userId)) {
            // 주문 취소로 상태 변경
            order.setOrderStatus(OrderStatus.ORDER_CANCELLED);

            for (OrderProductOption orderProductOption : order.getOrderProducts().get(0).getOrderProductOptions()) {
                for (ProductOption productOption : productOptions) {
                    if (orderProductOption.getOrderOptionNum() == productOption.getOptionNum()) {
                        // 취소한 만큼 상품 재고 증가
                        productOption.increaseOptionQuantity(orderProductOption.getOrderOptionQuantity());
                        // 증가한 후 재고 정보 보존
                        productRepository.save(productOption);
                    }
                }
            }
            // 주문 정보 보존
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> makeOrderHistory(User user) {
        return orderRepository.findAllByUser(user);
    }
}
