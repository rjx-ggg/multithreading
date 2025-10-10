package com.woniu.connection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.connection.entity.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {

    @Select("SELECT * FROM task t where t.id % #{shardTotal} = #{shardIndex} and t.fail_count<3 and t.task_status in (1,3) LIMIT #{count}")
    List<Task> getTaskList(@Param("shardIndex") int shardIndex, @Param("shardTotal") int shardTotal, @Param("count") int count);

    @Update("update task set task_status = 4 where id = #{task.id} and task_status in (1,3) and fail_count<3")
    int startTask(@Param("task") Task task);
}
