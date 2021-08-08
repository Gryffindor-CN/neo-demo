package com.neo.demo.shardingproxy.controller;

import com.neo.demo.shardingproxy.entity.Order;
import com.neo.demo.shardingproxy.entity.Orders;
import com.neo.demo.shardingproxy.repository.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping
    public void add() {
        long userId = 333;
        String status = "ok";

//        OrderItem item1 = new OrderItem();
//        item1.setItemId(12345678910L);
//        item1.setStatus(status);
//        item1.setUserId(userId);
//
//        OrderItem item2 = new OrderItem();
//        item1.setItemId(12345678911L);
//        item2.setStatus(status);
//        item2.setUserId(userId);
//
//        List<OrderItem> orderItems = new ArrayList<>();
//        orderItems.add(item1);
//        orderItems.add(item2);

        Order order = new Order();
//        order.setOrderId(123456789012345L);
//        order.setOrderItems(orderItems);
        order.setStatus(status);
        order.setUserId(userId);
        orderMapper.save(order);
    }

    @GetMapping("/{id}")
    public Order findById(@PathVariable long id) {
        return orderMapper.findById(id);
    }

    @GetMapping
    public Orders findAll() {
        List<Order> orders = orderMapper.findAll();
        return new Orders(orders);
    }
}
