package com.ydlclass.health.controller;

import com.ydlclass.health.common.constant.MessageConstant;
import com.ydlclass.health.common.constant.RedisMessageConstant;
import com.ydlclass.health.common.entity.Result;
import com.ydlclass.health.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@RestController
@Slf4j
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("submit.do")
    public Result submit(@RequestBody Map<String, String> map) {
        Integer id = null;
        // 校验验证码
        // 从Redis中取出验证码信息
        String telephone = map.get("telephone");
        String key = RedisMessageConstant.ORDER_MESSAGE + telephone + RedisMessageConstant.SENDTYPE_ORDER;
        Set<String> redisVerificationCode = redisTemplate.boundSetOps(key).members();
        // 获取用户传入验证码
        String validateCode = map.get("validateCode");

        if (redisVerificationCode == null || redisVerificationCode.size() == 0) {
            return new Result(false, MessageConstant.ORDER_FAIL);
        }

        for (String code : redisVerificationCode) {
            if (code.equals(validateCode)) {
                // 查找成功并找到匹配用户的value值
                try {
                    id = orderService.submit(map);
                } catch (Exception e) {
                    log.error("查找用户数据失败", e);
                }
            }
        }

        if (id == null) {
            return new Result(false, MessageConstant.ORDER_FAIL);
        }

        try {
            // 通知用户预约成功
            // SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, "validateCode");
            log.info("已发送短信,用户预约成功");
        } catch (Exception e) {
            // 短信重发  3次 
            // 调用Redis计时器
        }
        return new Result(true, MessageConstant.ORDER_SUCCESS, id);
    }


    @GetMapping("findById.do")
    public Result findById(@RequestParam("id") Integer id) {
        try {
            Map<String, Object> map = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        } catch (Exception e) {
            log.error("查询预约信息异常", e);
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }


}
