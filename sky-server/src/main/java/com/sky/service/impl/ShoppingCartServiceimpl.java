package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @BelongsPackage: com.sky.service.impl
 * @Author: ShiJun
 * @CreateTime: 2023-08-21  11:45
 * @Description: TODO
 * @Version: 1.0
 */
@Service
@Slf4j
public class ShoppingCartServiceimpl  implements ShoppingCartService {
  @Autowired
  private ShoppingCartMapper shoppingCartMapper;
  @Autowired
  private DishMapper dishMapper;
  @Autowired
  private SetmealMapper setmealMapper;

    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
   // 判断当前加入到购物车中的商品是否已经存在
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        // 如果已经存在了，只需要将数量加
        if (list !=null &&list.size()>0){
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber()+1);//updata shop set number ? where id =?
            shoppingCartMapper.updateNumberById(cart);

        }else {
            //如果不存在，需要插入一条购物数据
            //判断本次添加的是菜品话说套餐
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId!= null){
                //本次添加的是菜品
                Dish dish = dishMapper.getById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());


            }else{
                //本次是套餐
                 Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmael = setmealMapper.getById(setmealId);
                shoppingCart.setName(setmael.getName());
                shoppingCart.setImage(setmael.getImage());
                shoppingCart.setAmount(setmael.getPrice());


            }
              shoppingCart.setNumber(1);
              shoppingCart.setCreateTime(LocalDateTime.now());

               shoppingCartMapper.insert(shoppingCart);


        }

    }

    /*查看购物车*/
    @Override
    public List<ShoppingCart> showShoppongCart() {
        //获取id
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }
}