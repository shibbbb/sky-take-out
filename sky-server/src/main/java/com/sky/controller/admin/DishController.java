package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    public Result sava (@RequestBody DishDTO dishDTO){
       log.info("新增彩屏：{}",dishDTO);
       dishService.saveWithFlavor(dishDTO);
       //清理缓存数据
        String key="dish_"+dishDTO.getCategoryId();
        redisTemplate.delete(key);
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
        //将所有菜品缓存数据清理
        Set keys = redisTemplate.keys("dish_*");
         redisTemplate.delete(keys);
        return  Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getByid(@PathVariable long id){
        log.info("根据id查询菜品：{}",id);
        DishVO dishVO= dishService.getByIdWithFlavor(id);
        return  Result.success(dishVO);
    }
    @PutMapping
    @ApiOperation("修改菜品")
  public  Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);
        return Result.success();
  }
/**
 *   根据id查询菜品
 *   参数 categoryId
 * */
@GetMapping("/list")
@ApiOperation("根据分类id查询菜品")
public Result<List<Dish>>list(long categoryId){
    log.info("根据：{}查询菜品",categoryId);
     List<Dish> list=  dishService.list(categoryId);
    return Result.success(list);
}
/*
    起售停售菜品
  * * */


}