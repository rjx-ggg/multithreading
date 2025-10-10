package com.woniu.dynamicsql.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.dynamicsql.entity.Member;
import com.woniu.dynamicsql.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// MybatisPlus实现多租户
// 通过表字段（租户ID）进行数据逻辑隔离
@RestController
public class TenantController {
    @Autowired
    private MemberMapper memberMapper;

    // 测试插入操作
    @RequestMapping("/testTenant")
    public String testTenantId() {
        Member member = new Member();
        member.setNickName("顽皮豹");
        memberMapper.insert(member);
        return "success";
    }

    //测试查询操作
    @RequestMapping("/getCurrentTenantMember")
    public List<Member> getCurrentTenantMember() {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        return memberMapper.selectList(queryWrapper);
    }


}