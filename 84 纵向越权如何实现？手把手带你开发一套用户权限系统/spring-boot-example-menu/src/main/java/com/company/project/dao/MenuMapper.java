package com.company.project.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.project.entity.Menu;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * GlobalExceptionHandler
 * </p>
 *
 * @author 程序员蜗牛
 */
public interface MenuMapper extends BaseMapper<Menu> {

    int selectAuthByUserIdAndMenuCode(@Param("userId") Long userId, @Param("menuCode") String menuCode);

}
