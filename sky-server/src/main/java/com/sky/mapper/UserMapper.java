package com.sky.mapper;

import com.github.pagehelper.ISelect;
import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: sky-take-out
 * @BelongsPackage: com.sky.mapper
 * @Author: ShiJun
 * @CreateTime: 2023-08-15  16:10
 * @Description: TODO
 * @Version: 1.0
 */
@Repository
@Mapper
public interface UserMapper {
/*
* 根据openid查*/
@Select("select * from user where openid = #{openid}")
  User getByOpenid(String openid);
/*插入数据*/
    void insert(User user);
}