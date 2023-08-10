package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: sky-take-out
 * @BelongsPackage: com.sky.controller.admin
 * @Author: ShiJun
 * @CreateTime: 2023-08-08  15:06
 * @Description: TODO
 * @Version: 1.0
 */
@RestController

@RequestMapping("/admin/dish")
@Api(tags = "菜品接口")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    public Result sava (@RequestBody DishDTO dishDTO){
       log.info("新增彩屏：{}",dishDTO);
        return  Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page (DishPageQueryDTO dishPageQueryDTO){
    log.info("菜品分页查询{}",dishPageQueryDTO);
    PageResult pageResult=  dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam List<Long> ids){
        log.info("菜品批量删除{}",ids);
        dishService.deleteBath(ids);
        return  Result.success();
    }
}