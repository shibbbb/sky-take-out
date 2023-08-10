package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SetmealDishMapper {

    /*
    *    根据菜品id查询套餐id
    *
    *
    * */
     List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
