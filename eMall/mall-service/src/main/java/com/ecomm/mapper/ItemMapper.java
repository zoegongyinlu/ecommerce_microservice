package com.ecomm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecomm.domain.dto.OrderDetailDTO;
import com.ecomm.domain.po.Item;
import org.apache.ibatis.annotations.Update;


public interface ItemMapper extends BaseMapper<Item> {

    @Update("UPDATE item SET stock = stock - #{num} WHERE id = #{itemId}")
    void updateStock(OrderDetailDTO orderDetail);
}
