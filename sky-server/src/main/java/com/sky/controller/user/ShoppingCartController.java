package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @BelongsPackage: com.sky.controller.user
 * @Author: ShiJun
 * @CreateTime: 2023-08-21  11:39
 * @Description: TODO
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "用户端")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping("/add")
    @ApiOperation("添加购物车")
   public Result add( @RequestBody  ShoppingCartDTO shoppingCartDTO){
       log.info("添加购物车：{}",shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
       return  Result.success();
   }
/*
*   查看购物车
*
* */
   @GetMapping("/list")
   @ApiOperation("查看购物车")
   public Result<List<ShoppingCart>> list(){
       List<ShoppingCart>list=   shoppingCartService.showShoppongCart();
          return Result.success(list);
   }
   @DeleteMapping("/clean")
   @ApiOperation("清空购物车")
    public Result clean(){
       shoppingCartService.cleanShoppingCart();
       return  Result.success();
   }
}