package com.ecomm.item.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecomm.common.domain.PageDTO;
import com.ecomm.common.domain.PageQuery;
import com.ecomm.common.utils.BeanUtils;
import com.ecomm.item.domain.dto.ItemDTO;
import com.ecomm.item.domain.dto.OrderDetailDTO;
import com.ecomm.item.domain.po.Item;
import com.ecomm.item.service.IItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "商品管理相关接口")
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final IItemService itemService;

    @ApiOperation("分页查询商品")
    @GetMapping("/page")
    public PageDTO<ItemDTO> queryItemByPage(PageQuery query) {
        // 1.分页查询
        Page<Item> result = itemService.page(query.toMpPage("update_time", false));
        // 2.封装并返回
        return PageDTO.of(result, ItemDTO.class);
    }

    @ApiOperation("根据id批量查询商品")
    @GetMapping
    public List<ItemDTO> queryItemByIds(@RequestParam("ids") List<Long> ids){
        return itemService.queryItemByIds(ids);
    }

    @ApiOperation("根据id查询商品")
    @GetMapping("{id}")
    public ItemDTO queryItemById(@PathVariable("id") Long id) {
        return BeanUtils.copyBean(itemService.getById(id), ItemDTO.class);
    }

    @ApiOperation("新增商品")
    @PostMapping
    public void saveItem(@RequestBody ItemDTO item) {
        // 新增
        itemService.save(BeanUtils.copyBean(item, Item.class));
    }

    @ApiOperation("更新商品状态")
    @PutMapping("/status/{id}/{status}")
    public void updateItemStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status){
        Item item = new Item();
        item.setId(id);
        item.setStatus(status);
        itemService.updateById(item);
    }

    @ApiOperation("Update Goods")
    @PutMapping
    public void updateItem(@RequestBody ItemDTO item) {
        // 不允许修改商品状态，所以强制设置为null，更新时，就会忽略该字段
        item.setStatus(null);
        // 更新
        itemService.updateById(BeanUtils.copyBean(item, Item.class));
    }

    @ApiOperation("根据id删除商品")
    @DeleteMapping("{id}")
    public void deleteItemById(@PathVariable("id") Long id) {
        itemService.removeById(id);
    }

    @ApiOperation("批量扣减库存")
    @PutMapping("/stock/deduct")
    public void deductStock(@RequestBody List<OrderDetailDTO> items){
        itemService.deductStock(items);
    }
}
