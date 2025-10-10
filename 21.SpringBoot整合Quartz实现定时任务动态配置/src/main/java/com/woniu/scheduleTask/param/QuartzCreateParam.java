package com.woniu.scheduleTask.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
public class QuartzCreateParam  {
    @NotBlank(message = "任务类不能为空")
    private String jobClazz;

    @NotBlank(message = "任务类名不能为空")
    private String jobName;

    /**
     * 组名+任务类key组成唯一标识，所以如果这个参数为空，那么默认以任务类key作为组名
     */
    private String jobGroup;

    private Map<String, Object> jobDataMap;

    private String cron;

    private String description;
}
