package com.ecomm.cart.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ecomm.api.client.ItemClient;
import com.ecomm.api.dto.ItemDTO;
import com.ecomm.common.exception.BizIllegalException;
import com.ecomm.common.utils.BeanUtils;
import com.ecomm.common.utils.CollUtils;
import com.ecomm.common.utils.UserContext;

import com.ecomm.cart.domain.dto.CartFormDTO;
import com.ecomm.cart.domain.po.Cart;
import com.ecomm.cart.domain.vo.CartVO;
import com.ecomm.cart.mapper.CartMapper;
import com.ecomm.cart.service.ICartService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 */
@Service
@RequiredArgsConstructor

public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    private final ItemClient itemClient;

    @Override
    public void addItem2Cart(CartFormDTO cartFormDTO) {
        // 1.get userid
        Long userId = UserContext.getUser();


        if(checkItemExists(cartFormDTO.getItemId(), userId)){
            // 2.1.if exist updates the number
            baseMapper.updateNum(cartFormDTO.getItemId(), userId);
            return;
        }
        //check if is overloaded
        checkCartsFull(userId);

        // 3.新增购物车条目
        // 3.1.转换PO
        Cart cart = BeanUtils.copyBean(cartFormDTO, Cart.class);
        // 3.2.保存当前用户
        cart.setUserId(userId);
        // 3.3.保存到数据库
        save(cart);
    }

    @Override
    public List<CartVO> queryMyCarts() {
        // 1.查询我的购物车列表
        List<Cart> carts = lambdaQuery().eq(Cart::getUserId, UserContext.getUser()).list();
        if (CollUtils.isEmpty(carts)) {
            return CollUtils.emptyList();
        }

        // 2.转换VO
        List<CartVO> vos = BeanUtils.copyList(carts, CartVO.class);

        // 3.处理VO中的商品信息
        handleCartItems(vos);

        // 4.返回
        return vos;
    }

    /**
     * Add item in the cart
     * @param vos
     */
    private void handleCartItems(List<CartVO> vos) {
        //1. get item info
        Set<Long> itemIds = vos.stream().map(CartVO::getItemId).collect(Collectors.toSet());
//        List<ServiceInstance> instances = discoveryClient.getInstances("item_service");
//        if (CollUtils.isEmpty(instances)) {
//            return;
//        }
//
//        //2.2 write load balancer
//        ServiceInstance serviceInstance = instances.get(RandomUtil.randomInt(instances.size()));
//
//        //2. query item (use rest template to get the http response)
//       ResponseEntity<List<ItemDTO>> response = restTemplate.exchange(serviceInstance.getUri()+ "/items?ids={ids}", HttpMethod.GET, null,
//
//            new ParameterizedTypeReference<List<ItemDTO>>() {
//            }, Map.of("ids", CollUtils.join(itemIds, ",")));
//
//       //2.2 interpret
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            return;
//        }
//        List<ItemDTO> items = response.getBody();

        List<ItemDTO> items = itemClient.queryItemByIds(itemIds);

        if (CollUtils.isEmpty(items)) {
            return;
        }

        //3. from id to item map
        Map<Long, ItemDTO> itemMap = items.stream().collect(Collectors.toMap(ItemDTO::getId, Function.identity()));

        //4. write into VO
        for (CartVO cartVO : vos) {
            ItemDTO item = itemMap.get(cartVO.getItemId());
            if (item == null) {
                continue;
            }

            cartVO.setNewPrice(item.getPrice());
            cartVO.setStatus(item.getStatus());
            cartVO.setStock(item.getStock());
        }
    }

    @Override
    public void removeByItemIds(Collection<Long> itemIds) {
        // 1.构建删除条件，userId和itemId
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
        queryWrapper.lambda()
                .eq(Cart::getUserId, UserContext.getUser())
                .in(Cart::getItemId, itemIds);
        // 2.删除
        remove(queryWrapper);
    }

    private void checkCartsFull(Long userId) {
        int count = lambdaQuery().eq(Cart::getUserId, userId).count();
        if (count >= 10) {
            throw new BizIllegalException(StrUtil.format("用户购物车课程不能超过{}", 10));
        }
    }

    private boolean checkItemExists(Long itemId, Long userId) {
        int count = lambdaQuery()
                .eq(Cart::getUserId, userId)
                .eq(Cart::getItemId, itemId)
                .count();
        return count > 0;
    }
}
