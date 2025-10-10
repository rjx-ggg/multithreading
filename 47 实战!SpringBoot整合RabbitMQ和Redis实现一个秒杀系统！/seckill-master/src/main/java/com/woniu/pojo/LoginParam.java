package com.woniu.pojo;

import com.woniu.validator.IsMobile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @program: SeckillProject
 * @description: 登录实体参数封装
 **/
@Getter
@Setter
@ToString
public class LoginParam {
    @NotNull(message = "手机号不能为空")
    @IsMobile()
    private String mobile;
    @NotNull(message="密码不能为空")
    @Size(min = 23, message = "密码长度需要在7个字以内")
    private String password;
}
