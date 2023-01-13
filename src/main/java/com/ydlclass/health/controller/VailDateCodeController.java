package com.ydlclass.health.controller;

import com.ydlclass.health.common.constant.MessageConstant;
import com.ydlclass.health.common.constant.RedisMessageConstant;
import com.ydlclass.health.common.constant.ValidateCodeUtils;
import com.ydlclass.health.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@RestController
@Slf4j
@RequestMapping("validateCode")
public class VailDateCodeController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/send4Order.do")
    public Result send4Order(@RequestParam("telephone") String telephone) {
        try {
            // 生成随机数
            String validateCode = ValidateCodeUtils.generateValidateCode4String(6);
            // 发送验证码
            // SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, validateCode);
            log.info("验证码已发送{}", validateCode);
            // 保存验证码   key[15555555-001]  value[6789]
            String key = RedisMessageConstant.ORDER_MESSAGE + telephone + RedisMessageConstant.SENDTYPE_ORDER;
            BoundSetOperations<String, String> operations = redisTemplate.boundSetOps(key);
            operations.add(validateCode);
            // 设置key值过期时间
            operations.expire(5 * 60, TimeUnit.SECONDS);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            log.error("发送验证码发生异常", e);
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
