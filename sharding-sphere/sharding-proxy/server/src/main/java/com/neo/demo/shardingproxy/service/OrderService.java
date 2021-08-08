package com.neo.demo.shardingproxy.service;

import com.neo.demo.shardingproxy.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    void createOrder();

    Order findById(Long id);

    List<Order> findAll();
}
