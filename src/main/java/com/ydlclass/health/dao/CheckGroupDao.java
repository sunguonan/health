package com.ydlclass.health.dao;

import com.github.pagehelper.Page;
import com.ydlclass.health.common.pojo.CheckGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Mapper
public interface CheckGroupDao {

    void add(@Param("checkGroup") CheckGroup checkGroup);


    void addCheckGroupAndCheckItem(@Param("checkGroupAndCheckItemData") Map<String, Integer> checkGroupAndCheckItemData);

    Page<CheckGroup> findPage(@Param("queryString") String queryString);

    CheckGroup findById(@Param("id") Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(@Param("id") Integer id);
}
