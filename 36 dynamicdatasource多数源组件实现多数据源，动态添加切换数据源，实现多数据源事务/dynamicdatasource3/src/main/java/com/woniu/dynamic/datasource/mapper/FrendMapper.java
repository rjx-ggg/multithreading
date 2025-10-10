package com.woniu.dynamic.datasource.mapper;


import com.woniu.dynamic.datasource.entity.Frend;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/***
 * @Author 蜗牛
 */
public interface FrendMapper   {
    @Select("SELECT * FROM Frend")
    List<Frend> list();

    @Insert("INSERT INTO  frend(`name`) VALUES (#{name})")
    void save(Frend frend);
}