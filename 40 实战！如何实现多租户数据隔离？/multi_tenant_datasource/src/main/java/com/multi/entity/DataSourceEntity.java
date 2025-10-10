package com.multi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("datasource")
public class DataSourceEntity{

    private Integer dsId;

    private String tenantId;

    private String dsCode;

    private String jdbcUrl;

    private String username;

    private String password;

}
