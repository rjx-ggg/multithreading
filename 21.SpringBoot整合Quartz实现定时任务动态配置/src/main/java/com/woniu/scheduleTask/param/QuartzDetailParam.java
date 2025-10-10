package com.woniu.scheduleTask.param;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class QuartzDetailParam {
    @NotBlank(message = "任务类名不能为空")
    private String jobName;

    private String jobGroup;
}
