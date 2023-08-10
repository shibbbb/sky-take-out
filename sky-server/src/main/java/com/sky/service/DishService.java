package com.sky.service;

import com.sky.dto.DishDTO;
/*
*    新增菜品和对应的目录
*
* */
public interface DishService {
    public void saveWithFlavor(DishDTO dishDTO);
}
