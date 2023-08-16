package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

/*
*    新增菜品和对应的目录
*
* */
public interface DishService {
    public void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void deleteBath(List<Long> ids);

    DishVO getByIdWithFlavor(long id);


    void updateWithFlavor(DishDTO dishDTO);
}
