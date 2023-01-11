package com.ydlclass.health.service;

import com.ydlclass.health.common.entity.PageResult;
import com.ydlclass.health.common.entity.QueryPageBean;
import com.ydlclass.health.common.pojo.Setmeal;

import java.util.List;

/**
 * @author sunGuoNan
 * @version 1.0
 */
public interface SetMealService {

    void add(Integer[] checkgroupIds, Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> getAllSetmeal();
}
