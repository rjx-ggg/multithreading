package com.example.jwt.web;

import com.example.jwt.anno.NeedSplitParam;
import com.example.jwt.anno.SplitWorkAnnotation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 实战!用一个注解实现分批处理合并结果！
 */
@RestController
public class UserController {

    @PostMapping("/listDeviceDetail")
    @SplitWorkAnnotation(splitLimit = 20, splitGroupNum = 10)
    public List<Long> listDeviceDetail(@RequestBody
                                       @NeedSplitParam List<Long> deviceIds) {
        List<Long> longs = new ArrayList<>();
        longs.addAll(deviceIds);
        return longs;
    }
}
