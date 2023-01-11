package com.ydlclass.health.dao;

import com.ydlclass.health.common.pojo.OrderSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Mapper
public interface OrderSettingDao {


    long findCountByOrderDate(@Param("formatDate") String formatDate);


    void add(@Param("orderSetting") OrderSetting orderSetting);


    List<OrderSetting> orderSettingByMonth(@Param("hashMap") HashMap<String, String> hashMap);

    void editNumberByOrderDate(@Param("number") int number, @Param("formatDate") String formatDate);
}
