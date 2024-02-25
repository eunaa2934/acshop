package com.naeun2934.acshop;

import com.naeun2934.acshop.common.Constant;
import com.naeun2934.acshop.product.Product;
import com.naeun2934.acshop.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {

    private final LocaleResolver localeResolver;
    private final ProductService productService;

    @Autowired
    public HomeController(LocaleResolver localeResolver, ProductService productService) {
        this.localeResolver = localeResolver;
        this.productService = productService;
    }


    /**
     * 홈화면으로 이동
     * orderBy 기준에 따라 상품을 취득해 홈화면에 표시한다.
     *
     * @param orderBy 상품정렬순 (최신순, 조회순)
     * @return index.html 표시
     */
    @GetMapping("/")
    public String home(@RequestParam(name = "orderBy", required = false) String orderBy, Model model) {

        // orderBy 기준에 따라 상품 취득
        List<Product> products;
        if (!Constant.VIEWS.equals(orderBy)) {
            orderBy = Constant.UPDATE_DATE;
        }
        products = productService.findProducts(orderBy);

        model.addAttribute("products", products);

        return "/index";
    }


    /**
     * 국제화, 언어 설정
     * Header에서 선택한 언어를 화면에 표시한다.
     *
     * @param language 설정하고자 하는 언어
     * @return 현재 보고있던 화면으로 이동
     */
    @GetMapping("/changeLanguage")
    public String changeLanguage(String language, HttpServletRequest request, HttpServletResponse response) {

        // 선택한 언어 설정
        localeResolver.setLocale(request, response, new Locale(language));

        return "redirect:" + request.getHeader("Referer");
    }

}
