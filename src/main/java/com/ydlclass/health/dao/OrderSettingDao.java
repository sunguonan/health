package com.ydlclass.health.dao;

import com.ydlclass.health.common.pojo.OrderSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Mapper
public interface OrderSettingDao {


    long findCountByOrderDate(@Param("orderDate") Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    void add(@Param("orderSetting") OrderSetting orderSetting);


    List<OrderSetting> orderSettingByMonth(@Param("hashMap") HashMap<String, String> hashMap);
}
