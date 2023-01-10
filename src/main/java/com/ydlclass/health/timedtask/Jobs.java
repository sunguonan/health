package com.ydlclass.health.timedtask;

import com.ydlclass.health.common.constant.RedisConstant;
import com.ydlclass.health.util.QiNiuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Component
@Slf4j
public class Jobs {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Scheduled(cron = "0 0,1 0 1 1 ? *")
    public void timingDeleteImages() {
        // 找出Redis中两个键的差值
        Set<String> diffImageData =
                redisTemplate.boundSetOps(RedisConstant.SETMEAL_PIC_RESOURCES).diff(RedisConstant.SETMEAL_PIC_DB_RESOURCES);

        if (diffImageData == null || diffImageData.size() == 0)
            return;

        log.info("开始从七牛云中删除垃圾图片");
        for (String fileName : diffImageData) {
            // 七牛云删除垃圾图片
            QiNiuUtils.deleteFileFromQiNiu(fileName);
        }
        log.info("结束从七牛云中删除垃圾图片");

        ArrayList<String> deleteRedisKey = new ArrayList<>();
        deleteRedisKey.add(RedisConstant.SETMEAL_PIC_RESOURCES);
        deleteRedisKey.add(RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        log.info("开始删除Redis中的数据");
        // 全部删除Redis中的数据
        redisTemplate.delete(deleteRedisKey);
        log.info("结束删除Redis中的数据");
    }
}
