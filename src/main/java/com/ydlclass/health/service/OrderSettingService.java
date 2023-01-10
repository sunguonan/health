package com.ydlclass.health.service;

import com.ydlclass.health.common.pojo.OrderSetting;

import java.util.List;

/**
 * @author sunGuoNan
 * @version 1.0
 */
public interface OrderSettingService {
    void add(List<OrderSetting> readExcelData);

    List<OrderSetting> orderSettingByMonth(String date);
}
