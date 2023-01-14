package com.ydlclass.health.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydlclass.health.common.constant.MessageConstant;
import com.ydlclass.health.common.constant.RedisMessageConstant;
import com.ydlclass.health.common.entity.Result;
import com.ydlclass.health.common.pojo.Member;
import com.ydlclass.health.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@RestController
@Slf4j
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @PostMapping("check.do")
    public Result send4Login(@RequestBody Map<String, String> map, HttpServletResponse httpServletResponse) {
        // 1、校验用户输入的短信验证码是否正确，如果验证码错误则登录失败
        String validateCode = map.get("validateCode");
        String telephone = map.get("telephone");
        // Redis中查找对应的验证码信息
        Set<String> redisCode = redisTemplate.boundSetOps(telephone + RedisMessageConstant.SENDTYPE_LOGIN).members();

        if (redisCode == null || redisCode.size() == 0 || validateCode == null) {
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        for (String code : redisCode) {
            if (!code.equals(validateCode)) {
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }
        }


        // 2、如果验证码正确，则判断当前用户是否为会员，如果不是会员则自动完成会员注册
        Member member = memberService.findByTelephone(telephone);
        if (member == null) {
            // 完成会员注册
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            member.setPassword("12345");
            memberService.add(member);
        }

        // 3、向客户端写入Cookie，内容为用户手机号
        Cookie login = new Cookie("login", telephone);
        login.setPath("/");
        httpServletResponse.addCookie(login);

        // 4、将会员信息保存到Redis，使用手机号作为key，保存时长为30分钟
        String memberJson;
        try {
            ObjectMapper toJsonMap = new ObjectMapper();
            memberJson = toJsonMap.writeValueAsString(member);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        BoundSetOperations<String, String> memberMessage = redisTemplate.boundSetOps(telephone);
        memberMessage.expire(30, TimeUnit.MINUTES);
        memberMessage.add(memberJson);

        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }


}
