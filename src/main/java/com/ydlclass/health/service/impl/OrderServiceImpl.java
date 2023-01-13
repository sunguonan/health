package com.ydlclass.health.service.impl;

import com.ydlclass.health.common.pojo.Member;
import com.ydlclass.health.common.pojo.Order;
import com.ydlclass.health.common.pojo.OrderSetting;
import com.ydlclass.health.common.util.DateUtils;
import com.ydlclass.health.dao.MemberDao;
import com.ydlclass.health.dao.OrderDao;
import com.ydlclass.health.dao.OrderSettingMapper;
import com.ydlclass.health.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingMapper orderSettingDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;


    @Override
    public Integer submit(Map<String, String> map) throws Exception {
        // 1.检查用户选择的日期是否提前经过预约设置,如果不是则无法预约
        String orderDate = map.get("orderDate");
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        if (orderSetting == null) return null;

        // 2.检查用户所选择的日期是否约满了,如果已经约满了则无法预约
        // 可预约人数
        int number = orderSetting.getNumber();
        // 已预约人数
        int reservations = orderSetting.getReservations();
        if (reservations >= number) {
            return null;
        }

        // 3.检查用户是否重复预约(同一个用户一天只能预约一个套餐) 如果重复预约则无法预约
        String telephone = map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if (member != null) {
            // 通过手机获取memberId
            Integer id = member.getId();
            Order order = new Order();
            order.setSetmealId(id);
            order.setOrderDate(DateUtils.parseString2Date(orderDate));
            order.setSetmealId(Integer.parseInt(map.get("setmealId")));
            // 查询订单的详细信息
            List<Order> orderList = orderDao.findByCondition(order);
            if (orderList != null && orderList.size() > 0) {
                return null;
            }
        } else {
            // 4.检查用户是否为会员 如果是会员直接完成预约 如果不是会员 自动进行注册并预约
            member = new Member();
            member.setName(map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard(map.get("idCard"));
            member.setSex(map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }

        // 5.预约成功，更新当日的已预约人数
        Order order = new Order();
        order.setMemberId(member.getId());// 设置会员ID
        order.setOrderDate(DateUtils.parseString2Date(orderDate));// 预约日期
        order.setOrderType(map.get("orderType"));// 预约类型
        order.setOrderStatus(Order.ORDERSTATUS_NO);// 到诊状态
        order.setSetmealId(Integer.parseInt(map.get("setmealId")));// 套餐ID
        orderDao.add(order);
        // 6.更新预约人数
        orderSetting.setReservations(orderSetting.getReservations() + 1);// 设置已预约人数+1
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return order.getId();
    }

    @Override
    public Map<String, Object> findById(Integer id) throws Exception {
        // 获取数据
        Map<String, Object> map = orderDao.findById4Detail(id);
        // 转化日期
        Date orderDate = (Date) map.get("orderDate");
        String date = DateUtils.parseDate2String(orderDate);
        map.put("orderDate", date);
        return map;
    }
}
