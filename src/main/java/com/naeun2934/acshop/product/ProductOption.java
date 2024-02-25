package com.naeun2934.acshop.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product_option_po")
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "option_num")
    private int optionNum;

    @Column(name = "option_name")
    private String optionName;

    @Column(name = "option_price")
    private BigDecimal optionPrice;

    @Column(name = "option_quantity")
    private int optionQuantity;

    @OneToMany(mappedBy = "productOption", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    public ProductOption(int optionNum, String optionName, BigDecimal optionPrice, int optionQuantity
    ) {
        this.optionNum = optionNum;
        this.optionName = optionName;
        this.optionPrice = optionPrice;
        this.optionQuantity = optionQuantity;
    }

    public void addProductImage(ProductImage productImage) {
        this.productImages.add(productImage);
        productImage.setProductOption(this);
    }

    public void removeOptionQuantity(int orderOptionQuantity) {
        this.optionQuantity -= orderOptionQuantity;
    }

    public void increaseOptionQuantity(int orderOptionQuantity) {
        this.optionQuantity += orderOptionQuantity;
    }
}
