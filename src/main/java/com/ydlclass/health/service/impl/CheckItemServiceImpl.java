package com.ydlclass.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ydlclass.health.common.entity.PageResult;
import com.ydlclass.health.common.entity.QueryPageBean;
import com.ydlclass.health.common.pojo.CheckItem;
import com.ydlclass.health.dao.CheckItemDao;
import com.ydlclass.health.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Service
@Slf4j
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;


    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        log.info("开始分页查询");
        // PageHelper分页 底层调用threadLocal 本地线程 查询数据
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<CheckItem> page = checkItemDao.findPage(queryPageBean.getQueryString());
        log.info("结束分页查询");
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delete(Integer id) {
        log.info("查询检查组是否有绑定数据");
        // 利用检查项id 去关联的检查组查询 如果查询出有绑定的数据 则删除失败
        long countByCheckItemId = checkItemDao.findCountByCheckItemId(id);
        if (countByCheckItemId > 0) {
            throw new RuntimeException("检查组引用检查项");
        }
        log.info("查询检查组是否有绑定数据结束");
        checkItemDao.delete(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
