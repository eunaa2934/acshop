package com.naeun2934.acshop.seller;

import com.naeun2934.acshop.common.Address;
import com.naeun2934.acshop.product.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seller_s")
public class Seller extends Product {

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private final List<Product> products = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seller_id")
    private Long id;
    @Column(name = "seller_email")
    private String sellerEmail;
    @Column(name = "seller_name")
    private String sellerName;
    @Column(name = "seller_pwd")
    private String sellerPwd;
    @Embedded
    private Address address;

    public Seller(String sellerEmail, String sellerName, String sellerPwd, Address address) {
        this.sellerEmail = sellerEmail;
        this.sellerName = sellerName;
        this.sellerPwd = sellerPwd;
        this.address = address;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setSeller(this);
    }
}
