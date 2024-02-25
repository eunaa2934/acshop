package com.naeun2934.acshop.user;

import com.naeun2934.acshop.common.Address;
import com.naeun2934.acshop.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_u")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_pwd")
    private String userPwd;

    @Column(name = "user_new_pwd")
    private String userNewPwd;

    @Column(name = "user_provider")
    @Enumerated(EnumType.STRING)
    private UserProvider userProvider;

    @Column(name = "email_code")
    private String emailCode;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public User(String userEmail, String userName, String userPwd, String userNewPwd, UserProvider userProvider,
                String emailCode, Address address) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userNewPwd = userNewPwd;
        this.userProvider = userProvider;
        this.emailCode = emailCode;
        this.address = address;
    }

    public User(Long id, String userEmail, String userName, Address address, UserProvider userProvider) {
        this.id = id;
        this.userEmail = userEmail;
        this.userName = userName;
        this.address = address;
        this.userProvider = userProvider;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }
}
