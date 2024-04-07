package com.naeun2934.acshop.order;

public enum OrderStatus {
    // 입금전
    PAYMENT_PENDING,
    // 상품준비중
    PREPARING_FOR_PRODUCT,
    // 주문 취소
    ORDER_CANCELLED,
    // 배송준비중
    PREPARING_FOR_SHIPMENT,
    // 배송중
    SHIPPED,
    // 배송완료
    DELIVERED,
    // 반품요청
    RETURN_REQUESTED,
    // 교환요청
    EXCHANGE_REQUESTED,
    // 구매확정
    PURCHASE_CONFIRMED,
}
