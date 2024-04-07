package com.naeun2934.acshop.order;

import com.naeun2934.acshop.common.Address;
import com.naeun2934.acshop.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_o")
public class Order {

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderProduct> orderProducts = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotEmpty
    @Column(name = "order_user_email")
    private String orderUserEmail;

    @NotEmpty
    @Column(name = "order_user_name")
    private String orderUserName;

    @Embedded
    @Valid
    private Address orderAddress;

    @Column(name = "order_status")
    private OrderStatus orderStatus;

    public Order(String orderUserEmail, String orderUserName, Address orderAddress, OrderStatus orderStatus
    ) {
        this.orderUserEmail = orderUserEmail;
        this.orderUserName = orderUserName;
        this.orderAddress = orderAddress;
        this.orderStatus = orderStatus;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }
}
