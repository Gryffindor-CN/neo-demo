package com.neo.demo.shardingjdbc.entity;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class OrdersResp {
    private List<Order> orders;

    public OrdersResp() {}

    public OrdersResp(List<Order> orders) {
        this.orders = orders;
    }

    public static OrdersResp fromJson(String json) {
        return JSON.parseObject(json, OrdersResp.class);
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
