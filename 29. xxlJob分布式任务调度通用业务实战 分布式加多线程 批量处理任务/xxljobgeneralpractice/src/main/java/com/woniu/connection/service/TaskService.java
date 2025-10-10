package com.woniu.connection.service;


import com.woniu.connection.entity.Task;

import java.util.List;

public interface TaskService {

    /**
     * @param taskId   任务id
     * @param status   任务状态
     * @param errorMsg 错误信息
     */
    void saveTaskFinishStatus(int taskId, Integer status, String errorMsg);

    /**
     * 查询任务
     * @param shardIndex
     * @param shardTotal
     * @param num
     * @return
     */
    List<Task> getTaskList(int shardIndex, int shardTotal, int num);

    /**
     * 开启任务 把他的状态设置为4 正在处理
     * @param task
     */
    boolean startTask(Task task);


}
