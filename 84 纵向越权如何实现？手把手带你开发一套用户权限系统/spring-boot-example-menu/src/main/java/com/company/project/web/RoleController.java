package com.company.project.web;

import com.company.project.config.aspect.CheckPermissions;
import com.company.project.entity.Role;
import com.company.project.entity.dto.RoleDTO;
import com.company.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 纵向越权如何实现？
 * 手把手带你开发一套用户权限系统
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @CheckPermissions(value="roleMgr:list")
    @PostMapping(value = "/queryRole")
    public List<Role> queryRole(RoleDTO roleDTO){
        return roleService.findAll();
    }

}
