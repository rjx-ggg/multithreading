package com.woniu.demo.service.convert;

import com.woniu.demo.entity.UserDO;
import com.woniu.demo.model.request.InsertUserReq;
import com.woniu.demo.model.request.UpdateUserReq;

public class UserConvert {
    public static UserDO toDOWhenSave(InsertUserReq insertUserReq) {
        UserDO userDO = new UserDO();
        userDO.setUserName(insertUserReq.getUserName());
        userDO.setUserPhone(insertUserReq.getUserPhone());
        return userDO;
    }


}
