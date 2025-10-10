package com.woniu.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.demo.entity.OperationLogVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * sys_log 日志表 mapper 持久化接口
 */
@Mapper
public interface LogMapper extends BaseMapper<OperationLogVo> {

}
