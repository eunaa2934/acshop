package com.naeun2934.acshop.order;

import com.naeun2934.acshop.common.Constant;
import com.naeun2934.acshop.user.User;
import com.naeun2934.acshop.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * 주문 화면으로 이동
     *
     * @param orderProduct 주문한 상품에 관한 정보
     * @return orderForm.html 표시
     */
    @GetMapping("/orderForm")
    public String orderForm(@ModelAttribute OrderProduct orderProduct, Model model,
                            Principal principal, @SessionAttribute(name = Constant.LOGIN_USERID, required = false) Long userId,
                            HttpSession httpSession) {

        User user = null;

        // 세션에 정보가 없으면 Principal로 부터 조회
        if (userId == null) {
            String userEmail = principal.getName();
            user = userService.findByUserEmail(userEmail);
            httpSession.setAttribute(Constant.LOGIN_USEREMAIL, user.getUserEmail());
            httpSession.setAttribute(Constant.LOGIN_USERID, user.getId());
            userId = user.getId();
        }

        Order order = orderService.makeOrder(userId, orderProduct);

        model.addAttribute("order", order);

        return "/order/orderForm";
    }

    /**
     * 주문 처리
     *
     * @param order 주문관련 정보
     * @return 주문 성공 유무에 따른 화면 표시
     */
    @PostMapping("/placeAnOrder")
    public String placeAnOrder(@Validated @ModelAttribute Order order, BindingResult bindingResult, Principal principal, @SessionAttribute(name = Constant.LOGIN_USERID, required = false) Long userId, HttpSession httpSession) {

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            order = orderService.makeOrder(userId, order.getOrderProducts().get(0));
            return "/order/orderForm";
        }

        User user;

        // 세션에 정보가 없으면 Principal로 부터 조회
        if (userId == null) {
            String userEmail = principal.getName();
            user = userService.findByUserEmail(userEmail);
            httpSession.setAttribute(Constant.LOGIN_USEREMAIL, user.getUserEmail());
            httpSession.setAttribute(Constant.LOGIN_USERID, user.getId());
        } else {
            user = userService.findOne(userId);
        }

        try {
            orderService.placeAnOrder(order, user);
            return "redirect:/orders/orderCompleteForm";
        } catch (IllegalStateException e) {
            return "redirect:/products/" + order.getOrderProducts().get(0).getProduct().getId();
        }
    }

    /**
     * 주문 성공 화면으로 이동
     *
     * @return orderCompleteForm.html 표시
     */
    @GetMapping("/orderCompleteForm")
    public String orderCompleteForm() {

        return "/order/orderCompleteForm";
    }

    /**
     * 주문 이력 화면으로 이동
     *
     * @return orderHistoryForm.html 표시
     */
    @GetMapping("/orderHistoryForm")
    public String orderHistoryForm(Principal principal, @SessionAttribute(name = Constant.LOGIN_USERID, required = false) Long userId, HttpSession httpSession, Model model) {

        User user;

        // 세션에 정보가 없으면 Principal로 부터 조회
        if (userId == null) {
            String userEmail = principal.getName();
            user = userService.findByUserEmail(userEmail);
            httpSession.setAttribute(Constant.LOGIN_USEREMAIL, user.getUserEmail());
            httpSession.setAttribute(Constant.LOGIN_USERID, user.getId());
        } else {
            user = userService.findOne(userId);
        }

        // 주문 정보 가져오기
        List<Order> orders = orderService.makeOrderHistory(user);

        model.addAttribute("orders", orders);

        return "/order/orderHistoryForm";
    }

    /**
     * 주문 취소 처리
     *
     * @param orderId 취소 하려는 주문번호
     * @return orderHistoryForm.html 표시
     */
    @PostMapping("/cancel")
    public String orderCancel(@RequestParam Long orderId, @SessionAttribute(name = Constant.LOGIN_USERID, required = false) Long userId) {

        orderService.cancelOrder(userId, orderId);

        return "redirect:/orders/orderHistoryForm";
    }


}
