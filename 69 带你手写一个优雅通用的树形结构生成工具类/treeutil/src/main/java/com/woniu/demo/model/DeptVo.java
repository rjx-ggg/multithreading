package com.woniu.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeptVo {
    private int deptId;
    private int parentId;
    private List<Dept> children;

}
