package com.ydlclass.health.dao;


import com.ydlclass.health.common.pojo.OrderSetting;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderSettingMapper {
    public void add(OrderSetting orderSetting);

    // 更新可预约人数
    public void editNumberByOrderDate(OrderSetting orderSetting);

    // 更新已预约人数
    public void editReservationsByOrderDate(OrderSetting orderSetting);

    public long findCountByOrderDate(Date orderDate);

    // 根据日期范围查询预约设置信息
    public List<OrderSetting> getOrderSettingByMonth(Map date);

    // 根据预约日期查询预约设置信息
    public OrderSetting findByOrderDate(String orderDate);

}
