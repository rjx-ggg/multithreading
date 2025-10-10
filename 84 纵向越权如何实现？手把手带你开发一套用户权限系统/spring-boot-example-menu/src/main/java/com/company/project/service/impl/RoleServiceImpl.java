package com.company.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.dao.RoleMapper;
import com.company.project.entity.Role;
import com.company.project.entity.dto.RoleDTO;
import com.company.project.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 程序员蜗牛
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Override
    public List<Role> findAll() {
        return super.baseMapper.findAll();
    }

    @Override
    public boolean add(RoleDTO roleDTO) {
        // todo...
        return false;
    }

    @Override
    public boolean delete(RoleDTO roleDTO) {
        // todo...
        return false;
    }
}
