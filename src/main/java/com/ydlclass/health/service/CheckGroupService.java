package com.ydlclass.health.service;

import com.ydlclass.health.common.pojo.CheckGroup;

/**
 * @author sunGuoNan
 * @version 1.0
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

}
