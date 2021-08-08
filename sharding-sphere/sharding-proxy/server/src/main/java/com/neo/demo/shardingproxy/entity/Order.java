package com.neo.demo.shardingproxy.entity;

import com.alibaba.fastjson.JSON;

public class Order {

    private Long orderId;

    private Long userId;

    private String status;

    public static Order fromJson(String json) {
        return JSON.parseObject(json, Order.class);
    }

    public String toJson() {
        return JSON.toJSONString(this);
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
