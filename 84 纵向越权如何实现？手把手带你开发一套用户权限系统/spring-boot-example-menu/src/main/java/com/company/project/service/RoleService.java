package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.Role;
import com.company.project.entity.dto.RoleDTO;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author 程序员蜗牛
 * @since 2020-06-28
 */
public interface RoleService extends IService<Role> {

    List<Role> findAll();

    boolean add(RoleDTO roleDTO);

    boolean delete(RoleDTO roleDTO);

}
