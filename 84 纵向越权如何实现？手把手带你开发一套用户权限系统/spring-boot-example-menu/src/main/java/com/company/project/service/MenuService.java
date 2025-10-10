package com.company.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.entity.Menu;
import com.company.project.entity.vo.MenuVo;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author 程序员蜗牛
 */
public interface MenuService extends IService<Menu> {

    void addMenu(Menu menu);

    List<MenuVo> queryMenuTree();

    List<MenuVo> queryMenus(Long userId);
}
