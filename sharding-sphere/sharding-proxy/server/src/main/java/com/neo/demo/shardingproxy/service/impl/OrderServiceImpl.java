package com.neo.demo.shardingproxy.service.impl;

import com.neo.demo.shardingproxy.entity.Order;
import com.neo.demo.shardingproxy.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public void createOrder() {

    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }
}
