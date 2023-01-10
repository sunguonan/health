package com.ydlclass.health.service.impl;

import com.ydlclass.health.common.pojo.OrderSetting;
import com.ydlclass.health.dao.OrderSettingDao;
import com.ydlclass.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Service
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> list) {
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                // 判断当前日期是否已经进行了预约设置
                long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (countByOrderDate > 0) {
                    // 已经进行了预约设置，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                } else {
                    // 没有进行预约设置，执行插入操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<OrderSetting> orderSettingByMonth(String date) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("begin", date + "-01");
        hashMap.put("end", date + "-31");
        return orderSettingDao.orderSettingByMonth(hashMap);
    }

}
