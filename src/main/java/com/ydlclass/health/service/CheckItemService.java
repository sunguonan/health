package com.ydlclass.health.service;

import com.ydlclass.health.common.entity.PageResult;
import com.ydlclass.health.common.entity.QueryPageBean;
import com.ydlclass.health.common.pojo.CheckItem;

/**
 * @author sunGuoNan
 * @version 1.0
 */
public interface CheckItemService {
    void add(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    void edit(CheckItem checkItem);

    CheckItem findById(Integer id);
}
