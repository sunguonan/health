package com.ydlclass.health.service.impl;

import com.ydlclass.health.common.pojo.Setmeal;
import com.ydlclass.health.dao.SetMealDao;
import com.ydlclass.health.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    @Override
    public void add(Integer[] checkgroupIds, Setmeal setmeal) {
        // 添加套餐信息
        setMealDao.add(setmeal);
        // 绑定套餐和检查组数据项
        reAssociation(setmeal.getId(), checkgroupIds);
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
