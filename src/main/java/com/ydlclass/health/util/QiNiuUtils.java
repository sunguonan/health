package com.ydlclass.health.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sunGuoNan
 * @version 1.0
 */
@Slf4j
public class QiNiuUtils {
    private static final String accessKey = "DzxvEn3U4c8I7WrlIidQQfiiUPZLB91UGBHjY2CC";
    private static final String secretKey = "oDGHAa9SOp9g7taavfBcnP0roByrY74ciZzEpjRq";
    private static final String bucket = "space-sgn";

    /**
     * 上传文件 -- 文件名上传
     *
     * @param filePath
     * @param fileName
     */
    public static void upLoadQiNiuToFileName(String filePath, String fileName) {
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本

        UploadManager uploadManager = new UploadManager(cfg);


        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(filePath, fileName, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("上传成功文件的key值[{}]", putRet.key);
            log.info("上传成功文件的hash值[{}]", putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("上传失败发生异常", ex);
            log.error("获取失败响应体[{}]", r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }
    }

    /**
     * 上传文件 -- 使用byte数组上传
     *
     * @param bytes
     * @param fileName
     * @return void
     * @author sunGuoNan
     */
    public static void uploadQiNiuToBytes(byte[] bytes, String fileName) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, fileName, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("上传成功文件的key值[{}]", putRet.key);
            log.info("上传成功文件的hash值[{}]", putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(r.toString());
            try {
                log.error(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public static void deleteFileFromQiNiu(String fileName) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.region2());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
        } catch (QiniuException ex) {
            // 如果遇到异常，说明删除失败
            log.error("{}", ex.code());
            log.error(ex.response.toString());
        }
    }


}
