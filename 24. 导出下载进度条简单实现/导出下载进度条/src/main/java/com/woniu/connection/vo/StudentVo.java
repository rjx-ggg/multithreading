package com.woniu.connection.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StudentVo implements Serializable {

    private Integer id;
    private String name;
}
