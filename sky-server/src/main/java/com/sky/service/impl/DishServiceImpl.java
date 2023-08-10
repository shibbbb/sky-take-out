package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DisFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
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
    @Autowired
    private SetmealDishMapper setmealDishMapper;
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
/*茶品分页查询*/
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page=dishMapper.pageQuery(dishPageQueryDTO);

        return new  PageResult(page.getTotal(),page.getResult());
    }

    /*菜品删除*/
    @Transactional
    @Override
    public void deleteBath(List<Long> ids) {
        //判断当前菜品是否能够删除 是否存在起售的菜品
  for (Long id:ids){
     Dish dish=  dishMapper.getById(id);
     if (dish.getStatus() == StatusConstant.ENABLE){
         throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
     }
  }
        //判断当前菜品是否能够删除 是否被套餐关联
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
      if ( setmealIds !=null && setmealIds.size()>0){
          throw  new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);

      }

        //删除菜品表中的菜品数据
        for (Long id:ids ){
            dishMapper.deleteById(id);
            //删除菜品关联的口味数据
           dishFlavorMapper.deleteByDishId(id);
        }


    }
}