package com.ydlclass.health.service.impl;

import com.ydlclass.health.common.pojo.CheckGroup;
import com.ydlclass.health.dao.CheckGroupDao;
import com.ydlclass.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Service
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 插入检查组的数据
        checkGroupDao.add(checkGroup);

        for (Integer ids : checkitemIds) {
            Map<String, Integer> checkGroupAndCheckItemData = new HashMap<>(8);
            checkGroupAndCheckItemData.put("checkgroupId", checkGroup.getId());
            checkGroupAndCheckItemData.put("checkitemId", ids);
            // 插入检查组合检查项关联表的数据
            checkGroupDao.addCheckGroupAndCheckItem(checkGroupAndCheckItemData);
        }
    }
}
