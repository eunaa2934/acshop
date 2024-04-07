package com.naeun2934.acshop.product;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_category_pc")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_category_id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_desc")
    private String categoryDesc;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @Setter(AccessLevel.PRIVATE)
    private ProductCategory parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<ProductCategory> child = new ArrayList<>();

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public ProductCategory(
            String categoryName,
            String categoryDesc,
            ProductCategory parent
    ) {
        this.categoryName = categoryName;
        this.categoryDesc = categoryDesc;
        this.parent = parent;
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setProductCategory(this);
    }
}
