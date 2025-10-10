package com.multi.service;

import com.multi.dao.TUserMapper;
import com.multi.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private TUserMapper tUserMapper;

    public List<TUser> findAll() {
        return tUserMapper.selectList(null);
    }
}
