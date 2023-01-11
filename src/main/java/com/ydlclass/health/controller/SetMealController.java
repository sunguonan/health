package com.ydlclass.health.controller;

import com.ydlclass.health.common.constant.MessageConstant;
import com.ydlclass.health.common.constant.RedisConstant;
import com.ydlclass.health.common.entity.PageResult;
import com.ydlclass.health.common.entity.QueryPageBean;
import com.ydlclass.health.common.entity.Result;
import com.ydlclass.health.common.pojo.Setmeal;
import com.ydlclass.health.common.util.QiNiuUtils;
import com.ydlclass.health.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping("upload.do")
    public Result upload(@RequestParam("imgFile") MultipartFile multipartFile) {
        String fileNameSuffix = null;
        // 获取到源文件名
        String originalFileName = multipartFile.getOriginalFilename();
        if (originalFileName != null)
            // 截取文件后缀
            fileNameSuffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 生成新的文件名
        String fileName = UUID.randomUUID() + fileNameSuffix;

        try {
            // 上传七牛云存储
            QiNiuUtils.uploadQiNiuToBytes(multipartFile.getBytes(), fileName);
            // 把文件名存放到Redis中
            redisTemplate.boundSetOps(RedisConstant.SETMEAL_PIC_RESOURCES).add(fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            log.error("图片上传失败", e);
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @PutMapping("add.do")
    public Result add(@RequestParam("checkgroupIds") Integer[] checkgroupIds, @RequestBody Setmeal setmeal) {
        try {
            setMealService.add(checkgroupIds, setmeal);
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            log.error("添加套餐发生异常", e);
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @PostMapping("/findPage.do")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setMealService.findPage(queryPageBean);
    }

    @GetMapping("getAllSetmeal.do")
    public Result getAllSetmeal() {
        try {
            List<Setmeal> serviceList = setMealService.getAllSetmeal();
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, serviceList);
        } catch (Exception e) {
            log.error("查询套餐发生异常", e);
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }


}
