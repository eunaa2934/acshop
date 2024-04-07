package com.naeun2934.acshop.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "order_product_option_opo")
public class OrderProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_product_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    @Column(name = "order_option_num")
    private int orderOptionNum;

    @Column(name = "order_option_name")
    private String orderOptionName;

    @Column(name = "order_option_price")
    private BigDecimal orderOptionPrice;

    @Column(name = "order_option_quantity")
    private int orderOptionQuantity;

    public OrderProductOption(int orderOptionNum, String orderOptionName, BigDecimal orderOptionPrice, int orderOptionQuantity
    ) {
        this.orderOptionNum = orderOptionNum;
        this.orderOptionName = orderOptionName;
        this.orderOptionPrice = orderOptionPrice;
        this.orderOptionQuantity = orderOptionQuantity;
    }
}
