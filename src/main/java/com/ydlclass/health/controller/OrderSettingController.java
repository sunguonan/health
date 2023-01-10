package com.ydlclass.health.controller;

import com.ydlclass.health.common.constant.MessageConstant;
import com.ydlclass.health.common.entity.Result;
import com.ydlclass.health.common.pojo.OrderSetting;
import com.ydlclass.health.service.OrderSettingService;
import com.ydlclass.health.util.POIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@RestController
@Slf4j
@RequestMapping("ordersetting")
public class OrderSettingController {

    @Autowired
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload.do")
    public Result add(@RequestParam("excelFile") MultipartFile multipartFile) {
        try {
            List<String[]> list = POIUtils.readExcel(multipartFile);
            List<OrderSetting> readExcelData = new ArrayList<>();
            for (String[] strings : list) {
                OrderSetting orderSetting = new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1]));
                readExcelData.add(orderSetting);
            }
            orderSettingService.add(readExcelData);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            log.error("批量导入发生异常信息", e);
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }


    @GetMapping("getOrderSettingByMonth.do")
    public Result getOrderSettingByMonth(@RequestParam("date") String date) {
        try {
            List<OrderSetting> orderSettingList = orderSettingService.orderSettingByMonth(date);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, orderSettingList);
        } catch (Exception e) {
            log.error("预约信息发生异常信息", e);
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

}
