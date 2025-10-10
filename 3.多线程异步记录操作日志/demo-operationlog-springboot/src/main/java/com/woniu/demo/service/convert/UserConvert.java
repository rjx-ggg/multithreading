package com.woniu.demo.service.convert;

import com.woniu.demo.entity.UserDO;
import com.woniu.demo.model.request.UserReq;

public class UserConvert {
    public static UserDO toDOWhenSave(UserReq addReq) {
        UserDO userDO = new UserDO();
        userDO.setUserName(addReq.getUserName());
        userDO.setUserPhone(addReq.getUserPhone());
        return userDO;
    }
}
