package com.ecomm.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecomm.api.dto.ItemDTO;
import com.ecomm.api.dto.OrderDetailDTO;
import com.ecomm.common.exception.BizIllegalException;
import com.ecomm.common.utils.BeanUtils;

import com.ecomm.item.domain.po.Item;
import com.ecomm.item.mapper.ItemMapper;
import com.ecomm.item.service.IItemService;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.Collection;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {

    private final RedisTemplate<String, Object> redisTemplate;

    public ItemServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    @Transactional
    public void deductStock(List<OrderDetailDTO> items) {
        String sqlStatement = "com.ecomm.item.mapper.ItemMapper.updateStock";
        boolean r = false;
        try {
            r = executeBatch(items, (sqlSession, entity) -> sqlSession.update(sqlStatement, entity));
        } catch (Exception e) {
            throw new BizIllegalException("Exception in stock, insufficient stock!", e);
        }
        if (!r) {
            throw new BizIllegalException("Insufficient stock!！");
        }
    }

    @Override
    public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
        List<ItemDTO> result = new ArrayList<>();
        for (Long id : ids) {
            String key = "item:" + id;
            ItemDTO cachedItem = (ItemDTO) redisTemplate.opsForValue().get(key);
            if (cachedItem != null) {
                result.add(cachedItem);
            } else {
                Item item = getById(id);
                if (item != null) {
                    ItemDTO itemDTO = BeanUtils.copyProperties(item, ItemDTO.class);
                    redisTemplate.opsForValue().set(key, itemDTO, 1, TimeUnit.HOURS);
                    result.add(itemDTO);
                }
            }
        }
        return result;
    }
}
