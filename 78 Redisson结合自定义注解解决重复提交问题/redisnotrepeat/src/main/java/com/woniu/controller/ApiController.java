package com.woniu.controller;


import com.woniu.anno.NotRepeatDuplication;
import com.woniu.entity.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/")
@Slf4j
public class ApiController {


    /**
     * Redisson结合自定义注解解决重复提交问题
     * 插入记录
     * @return 结果
     */
    @NotRepeatDuplication(delaySeconds = 10)
    @PostMapping("/insert")
    public ResponseEntity<Address> insert(@RequestBody Address address) {
        // 插入
        log.info("address is {}", address);

        // 返回列表
        return ResponseEntity.ok().body(address);
    }
}
