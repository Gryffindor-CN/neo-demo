package com.neo.demo.shardingjdbc.controller;

import com.neo.demo.shardingjdbc.entity.*;
import com.neo.demo.shardingjdbc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public OrdersResp findAll() {
        List<Order> orders = orderRepository.findAll();
        return new OrdersResp(orders);
    }

    @GetMapping("{id}")
    public OrderResp findById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        Order o = order.get();
        return new OrderResp(o.getOrderId(), o.getUserId(), o.getStatus());
    }

    @PostMapping
    public void add(@RequestBody OrderReq or) {
        Order order = new Order();
        order.setUserId(or.getUserId());
        order.setStatus(or.getStatus());
        Order or1 = orderRepository.save(order);
    }
}
