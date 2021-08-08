package com.neo.demo.shardingjdbc.entity;

import java.util.List;

public class OrdersReq {

    List<OrderReq> orderReqs;

    public List<OrderReq> getOrderReqs() {
        return orderReqs;
    }

    public void setOrderReqs(List<OrderReq> orderReqs) {
        this.orderReqs = orderReqs;
    }
}
