package com.example.mapper;

import com.example.model.ProductStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductStockMapper {

    @Select("SELECT * FROM product_stock WHERE id = #{id}")
    ProductStock getProductById(Integer id); // 根据ID获取商品库存信息

    @Update("UPDATE product_stock SET stock_quantity = stock_quantity - #{quantity} WHERE id = #{id}")
    void reduceStock(@Param("id") Integer id, @Param("quantity") Integer quantity); // 减少指定ID的商品库存

    @Select("SELECT COUNT(*) FROM stock_alerts WHERE product_id = #{productId}")
    int getAlertCountForProduct(Integer productId); // 获取指定商品ID的预警次数

    @Select("SELECT * FROM product_stock WHERE stock_quantity < threshold")
    List<ProductStock> getLowStockProducts(); // 获取所有低于阈值的商品库存信息
}