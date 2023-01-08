package com.ydlclass.health;


import com.ydlclass.health.common.pojo.CheckItem;
import com.ydlclass.health.dao.CheckItemDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class HealthApplicationTests {

    @Autowired
    private CheckItemDao checkItemDao;


    @Test
    public void add() {
        CheckItem checkItem = new CheckItem();

        checkItem.setId(0);
        checkItem.setCode("");
        checkItem.setName("");
        checkItem.setSex("");
        checkItem.setAge("");
        checkItem.setRemark("");
        checkItem.setAttention("");
        checkItem.setPrice(0.0F);
        checkItem.setType("");

        checkItemDao.add(checkItem);
    }


    @Test
    public void test11() {
        // 日志的记录
        log.error("error信息");
        log.warn("warn信息");
        log.info("info信息");
        log.debug("debug信息");
        log.trace("trace信息");
        // 系统业务逻辑
        for (int i = 0; i < 100; i++) {
            System.out.println("------------------");
        }

    }

}
