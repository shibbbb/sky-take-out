package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: sky-take-out
 * @BelongsPackage: com.sky.controller.admin
 * @Author: ShiJun
 * @CreateTime: 2023-08-14  15:50 设置店铺营业状态
 * @Description: TODO
 * @Version: 1.0
 */
@RestController("userShopController")
@Slf4j
@Api("店铺相关接口")
@RequestMapping("/user/shop")

public class ShopController {
    private  static  final  String KEY="SHOW_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;



 @GetMapping("/status")
 @ApiOperation("获取店铺的营业状态")
 public Result<Integer>getStatus(){
     Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
     log.info("获取店铺的营业状态{}",status==1?"营业中":"打样中");
     return Result.success(status);
 }
}