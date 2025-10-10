package com.example.jwt.web;


import com.example.jwt.inte.HandleReturn;

import java.util.List;

/**
 * 集合List等合并策略
 *
 **/
public class MergeFunction implements HandleReturn {

    @Override
    public Object handleReturn(List results) {
        if (results == null) {
            return null;
        }
        if (results.size() <= 1) {
            return results.get(0);
        }
        //这里自己要知道具体返回类型
        List first = (List) results.get(0);
        for (int i = 1; i < results.size(); i++) {
            first.addAll((List) results.get(i));
        }
        return first;
    }
}