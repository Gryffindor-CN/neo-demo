package com.neo.demo.shardingproxy.repository;

import com.neo.demo.shardingproxy.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO t_order (user_id, status) VALUES (#{userId}, #{status})")
    void save(Order order);

    @Select("SELECT order_id, user_id, status FROM t_order WHERE order_id = #{id}")
    Order findById(Long id);

    @Select("SELECT order_id, user_id, status FROM t_order")
    List<Order> findAll();
}
