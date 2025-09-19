package com.ecomm.cart.controller;

import com.ecomm.cart.domain.dto.CartFormDTO;
import com.ecomm.cart.domain.po.Cart;
import com.ecomm.cart.domain.vo.CartVO;
import com.ecomm.cart.service.ICartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

/**
 * Cart Controller
 * 
 * REST controller for handling shopping cart operations in the eMall platform.
 * This controller provides endpoints for:
 * - Adding items to shopping cart
 * - Updating cart item quantities
 * - Removing items from cart
 * - Retrieving user's cart contents
 * - Batch operations for cart management
 * 
 * The controller integrates with the CartService for business logic implementation
 * and handles user-specific cart operations through authentication headers.
 * 
 * @author eMall Team
 * @version 1.0.0
 * @since 2024
 */
@Api(tags = "Shopping Cart API")
@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @ApiOperation("Add item to shopping cart")
    @PostMapping
    public void addItem2Cart(@Valid @RequestBody CartFormDTO cartFormDTO){
        cartService.addItem2Cart(cartFormDTO);
    }

    @ApiOperation("Update shopping cart data")
    @PutMapping
    public void updateCart(@RequestBody Cart cart){
        cartService.updateById(cart);
    }

    @ApiOperation("Delete item from shopping cart")
    @DeleteMapping("{id}")
    public void deleteCartItem(@Param("Cart item ID") @PathVariable("id") Long id){
        cartService.removeById(id);
    }

    @ApiOperation("Get shopping cart list")
    @GetMapping
    public List<CartVO> queryMyCarts(@RequestHeader(value = "user-header", required = false) String userId){

        return cartService.queryMyCarts();
    }

    @ApiOperation("Batch delete items from shopping cart")
    @ApiImplicitParam(name = "ids", value = "List of cart item IDs")
    @DeleteMapping
    public void deleteCartItemByIds(@RequestParam("ids") List<Long> ids){
        cartService.removeByItemIds(ids);
    }
}
