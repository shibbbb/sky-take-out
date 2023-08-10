package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DisFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @BelongsPackage: com.sky.service.impl
 * @Author: ShiJun
 * @CreateTime: 2023-08-08  15:17
 * @Description: TODO
 * @Version: 1.0
 *
 */
@Service
@Slf4j

public class DishServiceImpl  implements DishService {
  @Autowired
    private    DishMapper  dishMapper;
    @Autowired
    private DisFlavorMapper dishFlavorMapper;
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
         // 向菜品表插入1条数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.insert(dish);
        Long dishId = dish.getId();


        //取出集合数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if ( flavors !=null && flavors.size()>0){
            //向口味表插入n条数据
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
                    dishFlavorMapper.insertBatch(flavors);

        }
    }
}