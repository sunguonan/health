package com.ydlclass.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ydlclass.health.common.entity.PageResult;
import com.ydlclass.health.common.entity.QueryPageBean;
import com.ydlclass.health.common.pojo.CheckGroup;
import com.ydlclass.health.dao.CheckGroupDao;
import com.ydlclass.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 插入检查组的数据
        checkGroupDao.add(checkGroup);
        reAssociation(checkGroup.getId(), checkitemIds);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        // PageHelper分页 底层调用threadLocal 本地线程 查询数据
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    @Override
    public void edit(Integer[] checkitemIds, CheckGroup checkGroup) {
        // 修改检查组基本信息
        checkGroupDao.edit(checkGroup);
        // 清理检查组和检查项的关联关系
        checkGroupDao.deleteCheckGroupAndCheckItemRelation(checkGroup.getId());
        // 重新建立关系
        reAssociation(checkGroup.getId(), checkitemIds);

    }

    @Override
    public void delete(Integer id) {

        // 找出检查组和套餐的关联 如果套餐包含检查组 则失败
        Integer countRow = checkGroupDao.findCheckGroupAndSetmealRelation(id);
        if (countRow > 0) {
            throw new RuntimeException("检查组和套餐有关联,删除失败");
        }
        // 清空检查组和检查项关联信息
        checkGroupDao.DeleteCheckGroupAndCheckItemRelation(id);
        // 清空检查组信息
        checkGroupDao.deleteCheckGroupContent(id);


    }


    public void reAssociation(Integer checkgroupId, Integer[] checkitemIds) {
        for (Integer ids : checkitemIds) {
            Map<String, Integer> checkGroupAndCheckItemData = new HashMap<>(8);
            checkGroupAndCheckItemData.put("checkgroupId", checkgroupId);
            checkGroupAndCheckItemData.put("checkitemId", ids);
            checkGroupDao.buildCheckGroupAndCheckItemRelation(checkGroupAndCheckItemData);
        }
    }

}
