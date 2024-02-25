package com.naeun2934.acshop.product;

import com.naeun2934.acshop.order.OrderProduct;
import com.naeun2934.acshop.order.OrderProductOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Controller
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    @Value("${file.dir}")
    private String fileDir;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private static OrderProduct getOrderProduct(Product product) {
        OrderProduct orderProduct = new OrderProduct();

        // Product의 ProductOptions를 참고해, 주문하는 상품의 옵션정보 초기화
        // (다른 정보는 그대로 하고 수량만 모두 0으로)
        for (ProductOption productOption : product.getProductOptions()) {
            OrderProductOption orderProductOption = new OrderProductOption(productOption.getOptionNum(),
                    productOption.getOptionName(), productOption.getOptionPrice(),
                    0);
            orderProduct.addOrderProductOption(orderProductOption);
            orderProduct.setProduct(product);
        }
        return orderProduct;
    }

    @GetMapping("/{id}")
    public String productDetailForm(@PathVariable("id") Long productId, Model model) {

        // 상품 정보 취득
        Product product = productService.findOne(productId);

        // 조회수 증가
        product.addViews();

        // 조회수 증가한 상품 저장
        productService.saveProduct(product);

        // 주문할 상품에 관한 정보 생성
        OrderProduct orderProduct = getOrderProduct(product);
        orderProduct.setProduct(product);

        // 화면에 주문하는 상품에 관한 정보 전달
        model.addAttribute("orderProduct", orderProduct);

        return "/product/productDetailForm";
    }

    @ResponseBody
    @GetMapping("/productImage/{storedFileName}")
    public Resource loadProductImage(@PathVariable String storedFileName) throws
            MalformedURLException {
        return new UrlResource("file:" + fileDir + "productImages/" + storedFileName);
    }
}
