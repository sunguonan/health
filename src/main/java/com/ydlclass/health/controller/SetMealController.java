package com.ydlclass.health.controller;

import com.ydlclass.health.common.constant.MessageConstant;
import com.ydlclass.health.common.entity.Result;
import com.ydlclass.health.util.QiNiuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetMealController {
    /*  */
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
            QiNiuUtils.uploadQiNiuToBytes(multipartFile.getBytes(), fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, fileName);
        } catch (IOException e) {
            log.error("图片上传失败", e);
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }


}
