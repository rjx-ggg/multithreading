package com.woniu.connection.service;

import com.woniu.connection.util.AsyncUtil;
import com.woniu.connection.vo.StudentRequest;
import com.woniu.connection.vo.StudentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class ExportService {

    @Autowired
    StudentService studentService;

    /**
     * 导出方法
     */
    public String exportData(StudentRequest studentRequest, String key) throws IOException {
        List<StudentVo> studentVos = studentService.selectStudentDatas(studentRequest);
        AtomicInteger done = new AtomicInteger();
        AsyncUtil.setTotal(key, studentVos.size());
        studentVos.forEach(vo -> {
            //数据转换 模拟耗时操作
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                log.error("interrupt");
            }
            done.getAndIncrement();
            AsyncUtil.setDone(key, done.get());
        });
        //组织导出数据
        Map<String, Object> map = new HashMap<>();
        map.put("w", studentVos);
        // 生产excel文件 对象oss存储 返回url
        return getExcelUrl(map);
    }

    /**
     * 生产excel文件 对象oss存储 返回url
     */
    private String getExcelUrl(Map<String, Object> map) {
        return "我是oss url地址";
    }


}
