package com.woniu.muban;

/**
 * @className: IPay
 * @author: woniu
 * @date: 2024/3/6
 **/
public interface IPay {
     boolean supports(String code);

     void pay();

}
