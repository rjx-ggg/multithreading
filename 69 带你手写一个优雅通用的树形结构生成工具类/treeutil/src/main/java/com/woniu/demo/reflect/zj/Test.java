package com.woniu.demo.reflect.zj;

import com.woniu.demo.model.Dept;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {

    //  还不会生成树形结构？带你手写一个优雅通用的树形结构生成工具类
    public static void main(String[] args) {
        List<Dept> list = getList();

        Map<String, Object> stringObjectMap = TestTreeUtils.buildTree(list, Dept::getDeptId);
        System.out.println(stringObjectMap);
    }

    private static List<Dept> getList() {
        List<Dept> list = new ArrayList<>();
        Dept dept1 = new Dept();
        dept1.setDeptId(1);
        dept1.setParentId(0);
        list.add(dept1);
        Dept dept2 = new Dept();
        dept2.setDeptId(2);
        dept2.setParentId(1);
        list.add(dept2);
        Dept dept3 = new Dept();
        dept3.setDeptId(3);
        dept3.setParentId(1);
        list.add(dept3);
        Dept dept4 = new Dept();
        dept4.setDeptId(4);
        dept4.setParentId(2);
        list.add(dept4);
        Dept dept5 = new Dept();
        dept5.setDeptId(5);
        dept5.setParentId(4);
        list.add(dept5);
        return list;
    }
}
