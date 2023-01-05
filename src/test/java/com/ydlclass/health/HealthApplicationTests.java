package com.ydlclass.health;


import com.ydlclass.health.common.pojo.CheckItem;
import com.ydlclass.health.dao.CheckItemDao;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
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
        Logger logger = Logger.getLogger(HealthApplicationTests.class);

        // 日志记录输出
        logger.info("hello log4j");
        // 日志级别
        logger.fatal("fatal"); // 严重错误，一般会造成系统崩溃和终止运行
        logger.error("error"); // 错误信息，但不会影响系统运行
        logger.warn("warn"); // 警告信息，可能会发生问题
        logger.info("info"); // 程序运行信息，数据库的连接、网络、IO操作等
        logger.debug("debug"); // 调试信息，一般在开发阶段使用，记录程序的变量、参数等
        logger.trace("trace"); // 追踪信息，记录程序的所有流程信息
    }

}
