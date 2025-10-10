package com.woniu.scheduleTask.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuartzTriggerDetailDto {
    private String triggerName;
    private String triggerGroup;
    private String cron;
    private String description;

    private String triggerState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<Date> recentFireTimeList;
}

