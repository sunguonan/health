package com.ydlclass.health.dao;

import com.ydlclass.health.common.pojo.CheckGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Mapper
public interface CheckGroupDao {

    void add(@Param("checkGroup") CheckGroup checkGroup);


    void addCheckGroupAndCheckItem(@Param("checkGroupAndCheckItemData") Map<String, Integer> checkGroupAndCheckItemData);

}
