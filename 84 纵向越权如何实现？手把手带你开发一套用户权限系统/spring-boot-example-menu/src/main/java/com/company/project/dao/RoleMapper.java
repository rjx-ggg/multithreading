package com.company.project.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.project.entity.Role;

import java.util.List;

/**
 * <p>
 * GlobalExceptionHandler
 * </p>
 *
 * @author 程序员蜗牛
 */
public interface RoleMapper extends BaseMapper<Role> {


    List<Role> findAll();

}
