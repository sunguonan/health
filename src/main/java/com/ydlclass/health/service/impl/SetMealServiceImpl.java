package com.ydlclass.health.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ydlclass.health.common.constant.RedisConstant;
import com.ydlclass.health.common.entity.PageResult;
import com.ydlclass.health.common.entity.QueryPageBean;
import com.ydlclass.health.common.pojo.Setmeal;
import com.ydlclass.health.dao.SetMealDao;
import com.ydlclass.health.service.SetMealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Service
@Slf4j
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealDao setMealDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;

    @Value("${freeMarkerOutPutPath}")
    private String outPutPath;

    @Override
    public void add(Integer[] checkgroupIds, Setmeal setmeal) {
        // 添加套餐信息
        setMealDao.add(setmeal);
        // 绑定套餐和检查组数据项
        reAssociation(setmeal.getId(), checkgroupIds);
        // 在Redis中保存文件名
        redisTemplate.boundSetOps(RedisConstant.SETMEAL_PIC_DB_RESOURCES).add(setmeal.getImg());
        // 生成全部静态化页面
        // 1、一旦套餐的页面变动,首先要刷新套餐的列表项
        // 2、套餐页面变动后,套餐页面变动的详情页中也要刷新
        grenrateMobileStaticHtml();
    }

    private void grenrateMobileStaticHtml() {
        // 查询套餐的全部数据
        List<Setmeal> allSetmeal = setMealDao.getAllSetmeal();
        // 1.套餐列表的静态化页面
        grenrateMobileSetMealListHtml(allSetmeal);
        // 2.套餐详情的静态化页面
        grenrateMobileSetMealDetailHtml(allSetmeal);
    }

    // 抽离方法 专门存放数据、执行业务逻辑
    private void grenrateMobileSetMealListHtml(List<Setmeal> setmealList) {
        HashMap<String, Object> map = new HashMap<>();
        // 绑定页面数据
        map.put("setmealList", setmealList);
        // 调用模板方法
        grenrateHtml("mobile_setmeal.ftl", map, "m_setmeal.html");
    }


    // setmeal.checkGroups,表示在setmeal中有一个checkGroups数组,遍历结果即可
    // 所以用list封装一层 -->  List<Setmeal> setmealList
    private void grenrateMobileSetMealDetailHtml(List<Setmeal> setmealList) {
        // 遍历每一个的套餐项
        for (Setmeal setmeal : setmealList) {
            HashMap<String, Object> map = new HashMap<>();
            // 通过findById方法找出关联的每一个数据项(套餐、检查组、检查项)
            map.put("setmeal", setMealDao.findById(setmeal.getId()));
            // 模板生成每一个套餐详情的样式 setmeal_detail_${setmeal.id}.html 
            grenrateHtml("mobile_setmeal_detail.ftl", map, "setmeal_detail_" + setmeal.getId() + ".html");
        }
    }

    // 调用模板执行方法
    private void grenrateHtml(String getTemplateFileName, Map<String, Object> dataMap, String outPutFileName) {
        try (FileWriter fileWriter = new FileWriter(outPutPath + outPutFileName)) {
            // 获得模板配置
            Configuration configuration = freeMarkerConfig.getConfiguration();
            // 获取模板
            Template template = configuration.getTemplate(getTemplateFileName);
            // 输出
            template.process(dataMap, fileWriter);
        } catch (Exception e) {
            log.error("模板数据发生错误", e);
        }
    }


    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> setmealPage = setMealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(setmealPage.getTotal(), setmealPage.getResult());
    }

    @Override
    public List<Setmeal> getAllSetmeal() {
        return setMealDao.getAllSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }


    public void reAssociation(Integer setMealId, Integer[] checkgroupIds) {
        for (Integer ids : checkgroupIds) {
            Map<String, Integer> setmealAndCheckGroupData = new HashMap<>(8);
            setmealAndCheckGroupData.put("setmealid", setMealId);
            setmealAndCheckGroupData.put("checkgroupId", ids);
            setMealDao.buildSetMealAndCheckGroupId(setmealAndCheckGroupData);
        }
    }


}
