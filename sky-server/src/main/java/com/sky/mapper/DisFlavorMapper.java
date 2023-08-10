package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DisFlavorMapper {
    /*批量插入*/
    void insertBatch(List<DishFlavor> flavors);
/*
*
*   根据菜品id删除对应口味数据
* */

    @Delete("delete from dish_flavor where dish_id =#{dishId}")
    void deleteByDishId(Long dishId);
}
