package com.ecomm.cart.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecomm.cart.domain.po.Cart;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**

 */
public interface CartMapper extends BaseMapper<Cart> {

    @Update("UPDATE cart SET num = num + 1 WHERE user_id = #{userId} AND item_id = #{itemId}")
    void updateNum(@Param("itemId") Long itemId, @Param("userId") Long userId);
}
