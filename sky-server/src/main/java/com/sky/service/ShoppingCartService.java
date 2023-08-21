package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @BelongsPackage: com.sky.service
 * @Author: ShiJun
 * @CreateTime: 2023-08-21  11:43
 * @Description: TODO
 * @Version: 1.0
 */
public interface ShoppingCartService {

    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);
/*
*   查看购物车
* */
    List<ShoppingCart> showShoppongCart();
/*
*
*  清空购物车
* */
    void cleanShoppingCart();
}