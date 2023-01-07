package com.ydlclass.health.controller;


import com.ydlclass.health.common.constant.MessageConstant;
import com.ydlclass.health.common.entity.PageResult;
import com.ydlclass.health.common.entity.QueryPageBean;
import com.ydlclass.health.common.entity.Result;
import com.ydlclass.health.common.pojo.CheckGroup;
import com.ydlclass.health.service.CheckGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查组管理
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Autowired
    private CheckGroupService checkGroupService;

    private Logger logger = LoggerFactory.getLogger(CheckGroupController.class);

    /**
     * 新增检查组
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @RequestMapping("/add.do")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam("checkitemIds") Integer[] checkitemIds) {
        try {
            checkGroupService.add(checkGroup, checkitemIds);
            // 新增成功
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            logger.error("添加检查组发生异常", e);
            // 新增失败
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    @PostMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkGroupService.findPage(queryPageBean);
    }

    @GetMapping("/findById.do")
    public Result findById(@RequestParam("id") Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            logger.error("查询检查组发生异常", e);
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @GetMapping("/findCheckItemIdsByCheckGroupId.do")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("id") Integer id) {
        try {
            List<Integer> listData = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, listData);
        } catch (Exception e) {
            logger.error("查询检查组中的检查项发生异常", e);
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

}
