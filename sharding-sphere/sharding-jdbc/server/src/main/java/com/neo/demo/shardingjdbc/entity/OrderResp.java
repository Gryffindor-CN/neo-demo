package com.neo.demo.shardingjdbc.entity;

import java.util.List;

public class OrderResp {

    private Long orderId;

    private Long userId;

    private String status;

    private List<OrderItem> orderItems;

    public OrderResp(Long orderId, Long userId, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
