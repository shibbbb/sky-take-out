package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderDetailMapper {
    /*
      批量插入订单明细
    * */
    void insertBatch(List<OrderDetail> orderDetailList);
}
