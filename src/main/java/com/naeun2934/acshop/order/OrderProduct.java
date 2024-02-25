package com.naeun2934.acshop.order;

import com.naeun2934.acshop.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_product_op")
public class OrderProduct {

    @OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL)
    List<OrderProductOption> orderProductOptions = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public void addOrderProductOption(OrderProductOption orderProductOption) {
        this.orderProductOptions.add(orderProductOption);
        orderProductOption.setOrderProduct(this);
    }
}
