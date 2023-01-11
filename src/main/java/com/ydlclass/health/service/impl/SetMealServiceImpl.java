package com.ydlclass.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ydlclass.health.common.constant.RedisConstant;
import com.ydlclass.health.common.entity.PageResult;
import com.ydlclass.health.common.entity.QueryPageBean;
import com.ydlclass.health.common.pojo.Setmeal;
import com.ydlclass.health.dao.SetMealDao;
import com.ydlclass.health.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Service
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void add(Integer[] checkgroupIds, Setmeal setmeal) {
        // 添加套餐信息
        setMealDao.add(setmeal);
        // 绑定套餐和检查组数据项
        reAssociation(setmeal.getId(), checkgroupIds);
        // 在Redis中保存文件名
        redisTemplate.boundSetOps(RedisConstant.SETMEAL_PIC_DB_RESOURCES).add(setmeal.getImg());
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> setmealPage = setMealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(setmealPage.getTotal(), setmealPage.getResult());
    }

    @Override
    public List<Setmeal> getAllSetmeal() {
        return setMealDao.getAllSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }


    public void reAssociation(Integer setMealId, Integer[] checkgroupIds) {
        for (Integer ids : checkgroupIds) {
            Map<String, Integer> setmealAndCheckGroupData = new HashMap<>(8);
            setmealAndCheckGroupData.put("setmealid", setMealId);
            setmealAndCheckGroupData.put("checkgroupId", ids);
            setMealDao.buildSetMealAndCheckGroupId(setmealAndCheckGroupData);
        }
    }


}
