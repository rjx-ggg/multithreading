package com.woniu.connection.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.woniu.connection.entity.Task;
import com.woniu.connection.entity.TaskHistory;
import com.woniu.connection.mapper.TaskHistoryMapper;
import com.woniu.connection.mapper.TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TaskHistoryMapper taskHistoryMapper;

    @Override
    public void saveTaskFinishStatus(int taskId, Integer status, String errorMsg) {
        //查询这个任务
        Task task = taskMapper.selectById(taskId);
        if (task == null) {
            log.debug("更新任务状态时此任务:{}为空", taskId);
            return;
        }

        LambdaQueryWrapper<Task> queryWrapperById = new LambdaQueryWrapper<Task>().eq(Task::getId, taskId);
        if (3 == status) {
            //任务失败
            Task newTask = new Task();
            newTask.setTaskStatus(3); //处理失败
            newTask.setErrorMsg(errorMsg);
            newTask.setUpdateTime(new Date());
            newTask.setFailCount(task.getFailCount()+1);
            taskMapper.update(newTask, queryWrapperById);
            return;
        }

        //处理成功，更新状态
        if (2 == status) {
            dealSuccessTask(taskId, task);
        }

    }

    @Override
    public List<Task> getTaskList(int shardIndex, int shardTotal, int count) {
        return taskMapper.getTaskList(shardIndex, shardTotal, count);
    }

    @Override
    public boolean startTask(Task task) {
        int count = taskMapper.startTask(task);
        return count > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void dealSuccessTask(int taskId, Task task) {
        //如果处理成功将任务添加到历史记录表
        task.setTaskStatus(2);
        task.setUpdateTime(new Date());
        TaskHistory history = new TaskHistory();
        BeanUtils.copyProperties(task, history);
        taskHistoryMapper.insert(history);

        //如果处理成功将待处理表的记录删除
        taskMapper.deleteById(taskId);
    }
}
