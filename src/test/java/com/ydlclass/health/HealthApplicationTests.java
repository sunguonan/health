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
        
    }


}
