package com.ecomm.item.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecomm.api.dto.ItemDTO;
import com.ecomm.api.dto.OrderDetailDTO;
import com.ecomm.common.domain.PageDTO;
import com.ecomm.common.domain.PageQuery;
import com.ecomm.common.utils.BeanUtils;


import com.ecomm.item.domain.po.Item;
import com.ecomm.item.service.IItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Item Management API")
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final IItemService itemService;

    @ApiOperation("Paginated query for items")
    @GetMapping("/page")
    public PageDTO<ItemDTO> queryItemByPage(PageQuery query) {
        // 1. Paginated query
        Page<Item> result = itemService.page(query.toMpPage("update_time", false));
        // 2. Wrap and return
        return PageDTO.of(result, ItemDTO.class);
    }

    @ApiOperation("Query items by ID list")
    @GetMapping
    public List<ItemDTO> queryItemByIds(@RequestParam("ids") List<Long> ids) {

        return itemService.queryItemByIds(ids);
    }

    @ApiOperation("Query item by ID")
    @GetMapping("{id}")
    public ItemDTO queryItemById(@PathVariable("id") Long id) {
        return BeanUtils.copyBean(itemService.getById(id), ItemDTO.class);
    }

    @ApiOperation("Add new item")
    @PostMapping
    public void saveItem(@RequestBody ItemDTO item) {
        itemService.save(BeanUtils.copyBean(item, Item.class));
    }

    @ApiOperation("Update item status")
    @PutMapping("/status/{id}/{status}")
    public void updateItemStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        Item item = new Item();
        item.setId(id);
        item.setStatus(status);
        itemService.updateById(item);
    }

    @ApiOperation("Update item details")
    @PutMapping
    public void updateItem(@RequestBody ItemDTO item) {
        // Do not allow modification of item status, force to null so it is ignored during update
        item.setStatus(null);
        itemService.updateById(BeanUtils.copyBean(item, Item.class));
    }

    @ApiOperation("Delete item by ID")
    @DeleteMapping("{id}")
    public void deleteItemById(@PathVariable("id") Long id) {
        itemService.removeById(id);
    }

    @ApiOperation("Batch deduct stock")
    @PutMapping("/stock/deduct")
    public void deductStock(@RequestBody List<OrderDetailDTO> items) {
        itemService.deductStock(items);
    }
}
