package com.xlq.hospital.dao;

import com.xlq.hospital.model.Order;
import java.util.List;

public interface OrderDao {
    int deleteByPrimaryKey(String id);

    int insert(Order record);

    Order selectByPrimaryKey(String id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    List<Order> queryOrderByKey(Order order);
}