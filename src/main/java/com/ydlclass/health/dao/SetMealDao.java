package com.ydlclass.health.dao;

import com.ydlclass.health.common.pojo.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Mapper
public interface SetMealDao {

    void add(@Param("setmeal") Setmeal setmeal);


    void buildSetMealAndCheckGroupId(@Param("setmealAndCheckGroupData") Map<String, Integer> setmealAndCheckGroupData);
}
