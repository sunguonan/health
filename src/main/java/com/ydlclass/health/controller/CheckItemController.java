package com.ydlclass.health.controller;

import com.ydlclass.health.common.constant.MessageConstant;
import com.ydlclass.health.common.entity.PageResult;
import com.ydlclass.health.common.entity.QueryPageBean;
import com.ydlclass.health.common.entity.Result;
import com.ydlclass.health.common.pojo.CheckItem;
import com.ydlclass.health.service.CheckItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@RestController
@RequestMapping("/checkitem")
@Slf4j
public class CheckItemController {
    @Autowired
    private CheckItemService checkItemService;

    /**
     * 添加检查项
     *
     * @param checkItem
     * @return
     */
    @PostMapping("/add.do")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            log.error("添加时发生异常信息", e);
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    /**
     * 分页查询检查项
     *
     * @param queryPageBean
     * @return
     */
    @PostMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return checkItemService.findPage(queryPageBean);
    }

    /**
     * 删除检查项
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete.do")
    public Result delete(@RequestParam("id") Integer id) {
        try {
            checkItemService.delete(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            log.error("删除时发生异常信息", e);
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    /**
     * 修改检查项
     *
     * @param checkItem
     * @return
     */
    @PutMapping("/edit.do")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.edit(checkItem);
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            log.error("修改数据发生异常", e);
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    @GetMapping("/findById.do")
    public Result findById(@RequestParam("id") Integer id) {
        try {
            CheckItem checkItemData = checkItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemData);
        } catch (Exception e) {
            log.error("查询检查项数据发生异常", e);
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @GetMapping("/findAll.do")
    public Result findAll() {
        try {
            List<CheckItem> checkItemList = checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemList);
        } catch (Exception e) {
            log.error("查询所有检查项发生异常", e);
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


}
