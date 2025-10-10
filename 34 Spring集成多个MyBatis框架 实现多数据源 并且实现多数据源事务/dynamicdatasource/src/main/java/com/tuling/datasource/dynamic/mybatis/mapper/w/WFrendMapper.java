package com.tuling.datasource.dynamic.mybatis.mapper.w;


import com.tuling.datasource.dynamic.mybatis.entity.Frend;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/***
 * @Author 蜗牛
 */
public interface WFrendMapper {
    @Select("SELECT * FROM Frend")
    List<Frend> list();

    @Insert("INSERT INTO  frend(`name`) VALUES (#{name})")
    void save(Frend frend);
}