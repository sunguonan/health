package com.ydlclass.health.dao;

import com.github.pagehelper.Page;
import com.ydlclass.health.common.pojo.CheckItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Mapper
public interface CheckItemDao {
    void add(@Param("checkItem") CheckItem checkItem);

    // 调用分页插件后 直接返回Page即可
    Page<CheckItem> findPage(@Param("queryString") String queryString);

    void delete(@Param("id") Integer id);

    long findCountByCheckItemId(@Param("id") Integer id);

    void edit(@Param("checkItem") CheckItem checkItem);

    CheckItem findById(@Param("id") Integer id);

    List<CheckItem> findAll();
}
