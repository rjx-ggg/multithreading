package com.woniu.connection.controller;

import com.woniu.connection.exception.BusinessException;
import com.woniu.connection.service.ExportService;
import com.woniu.connection.util.AsyncUtil;
import com.woniu.connection.vo.RedisAsyResultData;
import com.woniu.connection.vo.Result;
import com.woniu.connection.vo.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TestController类
 */
@RestController
public class TestController {

    @Autowired
    ExportService exportService;

    /**
     * 异步导出，导出文件下载进度条简单实现 ！
     * 学生数据导出
     */
    @PostMapping("/export")
    public Result<String> exportData(@RequestBody StudentRequest studentRequest) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "studentData:" + formatter.format(new Date());
        AsyncUtil.submitTask(key, () -> {
            //获取并组织excel数据
            String url;
            try {
                url = exportService.exportData(studentRequest, key);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            return url;
        });
        return Result.success(key);
    }


    /**
     * 根据key获取导出接口
     * @param key
     * @return
     */
    @GetMapping("getRedisResult/{key}")
    public Result<RedisAsyResultData> getRedisResult(@PathVariable String key) {
        return Result.success(AsyncUtil.getResult(key));
    }


}
