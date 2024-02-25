package com.naeun2934.acshop.product;

import com.naeun2934.acshop.seller.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_p")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_short_description", length = 500)
    private String productShortDescription;

    @Column(name = "product_description", length = 5000)
    private String productDescription;

    @Column(name = "create_date")
    private Timestamp createDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    @Column(name = "views")
    private int views;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOption> productOptions = new ArrayList<>();

    public Product(String productName, String productShortDescription, String productDescription
    ) {
        this.productName = productName;
        this.productShortDescription = productShortDescription;
        this.productDescription = productDescription;
        this.createDate = new Timestamp(System.currentTimeMillis());
        this.updateDate = new Timestamp(System.currentTimeMillis());
        this.views = 0;
    }

    public void addProductOption(ProductOption productOption) {
        this.productOptions.add(productOption);
        productOption.setProduct(this);
    }

    // 조회수 증가
    public void addViews() {
        this.views++;
    }
}
