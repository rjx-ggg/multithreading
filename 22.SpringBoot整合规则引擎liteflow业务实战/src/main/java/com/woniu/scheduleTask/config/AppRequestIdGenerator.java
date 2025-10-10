package com.woniu.scheduleTask.config;

import cn.hutool.core.lang.Snowflake;
import com.yomahub.liteflow.flow.id.RequestIdGenerator;
import lombok.Data;

@Data
public class AppRequestIdGenerator implements RequestIdGenerator {

    public static final Snowflake flake = new Snowflake();

    @Override
    public String generate() {
        return flake.nextIdStr();
    }
}
