package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DisFlavorMapper {
    /*批量插入*/
    void insertBatch(List<DishFlavor> flavors);
}
