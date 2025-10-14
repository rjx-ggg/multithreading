package com.woniu.scheduleTask.testRun;

import com.woniu.scheduleTask.domain.UserInfo;
import org.springframework.stereotype.Component;

/**
 * ClassName: TestRun
 * Description:
 *
 * @Author 染染熊
 * @Version 1.0
 * @Create 2025/10/14 23:15
 */
@Component
public class TestRun {

    public void sayHello(UserInfo userInfo){
        System.out.println("hello "+userInfo.getUserName());
    }

    public void noParamMethod(){
        System.out.println("hello "+ "run noParamMethod");
    }




}
