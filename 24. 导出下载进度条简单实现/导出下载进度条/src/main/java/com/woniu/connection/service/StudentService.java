package com.woniu.connection.service;

import com.woniu.connection.vo.StudentRequest;
import com.woniu.connection.vo.StudentVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {


    public List<StudentVo> selectStudentDatas(StudentRequest studentRequest) {
        Integer batch = studentRequest.getBatch();
        List<StudentVo> list = new ArrayList<>(batch);
        for (int i = 0; i < batch; i++) {
            list.add(new StudentVo(i, "woniu" + i));
        }
        return list;
    }
}
