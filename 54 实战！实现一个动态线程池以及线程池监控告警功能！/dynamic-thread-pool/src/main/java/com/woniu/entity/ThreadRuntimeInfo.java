package com.woniu.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (ThreadRuntimeInfo)实体类
 */
public class ThreadRuntimeInfo implements Serializable {

    private static final long serialVersionUID = -90245606514427446L;
    /**
     * 自增主键ID
     */
    private Long id;
    /**
     * 项目ID
     */
    private String itemId;
    /**
     * 线程池名字
     */
    private String threadPoolName;
    /**
     * 核心线程数
     */
    private Integer corePoolSize;
    /**
     * 最大线程数
     */
    private Integer maximumPoolSize;
    /**
     * 队列容量
     */
    private Integer queueCapacity;
    /**
     * 线程空闲超时时间
     */
    private Long keepaliveTime;
    /**
     * 线程数
     */
    private Integer poolSize;
    /**
     * 活跃线程数
     */
    private Integer activeSize;
    /**
     * 队列元素
     */
    private Integer queueSize;
    /**
     * 队列剩余容量
     */
    private Integer queueRemainingCapacity;
    /**
     * 线程池曾经创建过的最大线程数
     */
    private Integer largestPoolSize;
    /**
     * 总任务计数
     */
    private Long taskCount;
    /**
     * 已完成任务计数
     */
    private Long completedTaskCount;
    /**
     * 当前负载
     */
    private Double currentLoad;
    /**
     * 峰值负载
     */
    private Double maximumLoad;
    /**
     * 任务队列类型
     */
    private String queueType;
    /**
     * 线程工厂类型
     */
    private String threadFactoryType;
    /**
     * 任务拒绝策略类型
     */
    private String rejectedHandlerType;
    /**
     * 索引时间戳
     */
    private Long timestamp;
    /**
     * 创建时间
     */
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public Long getKeepaliveTime() {
        return keepaliveTime;
    }

    public void setKeepaliveTime(Long keepaliveTime) {
        this.keepaliveTime = keepaliveTime;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    public Integer getActiveSize() {
        return activeSize;
    }

    public void setActiveSize(Integer activeSize) {
        this.activeSize = activeSize;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }

    public Integer getQueueRemainingCapacity() {
        return queueRemainingCapacity;
    }

    public void setQueueRemainingCapacity(Integer queueRemainingCapacity) {
        this.queueRemainingCapacity = queueRemainingCapacity;
    }

    public Integer getLargestPoolSize() {
        return largestPoolSize;
    }

    public void setLargestPoolSize(Integer largestPoolSize) {
        this.largestPoolSize = largestPoolSize;
    }

    public Long getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Long taskCount) {
        this.taskCount = taskCount;
    }

    public Long getCompletedTaskCount() {
        return completedTaskCount;
    }

    public void setCompletedTaskCount(Long completedTaskCount) {
        this.completedTaskCount = completedTaskCount;
    }

    public Double getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(Double currentLoad) {
        this.currentLoad = currentLoad;
    }

    public Double getMaximumLoad() {
        return maximumLoad;
    }

    public void setMaximumLoad(Double maximumLoad) {
        this.maximumLoad = maximumLoad;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getThreadFactoryType() {
        return threadFactoryType;
    }

    public void setThreadFactoryType(String threadFactoryType) {
        this.threadFactoryType = threadFactoryType;
    }

    public String getRejectedHandlerType() {
        return rejectedHandlerType;
    }

    public void setRejectedHandlerType(String rejectedHandlerType) {
        this.rejectedHandlerType = rejectedHandlerType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
