package com.ydlclass.health.dao;


import com.ydlclass.health.common.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderDao {
    void add(Order order);

    List<Order> findByCondition(Order order);

    Map<String, Object> findById4Detail(Integer id);

    Integer findOrderCountByDate(String date);

    Integer findOrderCountAfterDate(String date);

    Integer findVisitsCountByDate(String date);

    Integer findVisitsCountAfterDate(String date);

    List<Map> findHotSetmeal();
}
