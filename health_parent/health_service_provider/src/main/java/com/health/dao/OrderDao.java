package com.health.dao;

import com.health.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author HMR
 * @create 2021/8/15 9:49
 */
public interface OrderDao {
     List<Order> findByCondition(Order order);
     void add(Order order);
     Map findById4Detail(Integer id);



}
