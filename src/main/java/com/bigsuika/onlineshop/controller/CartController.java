package com.bigsuika.onlineshop.controller;

import com.bigsuika.onlineshop.model.Cart;
import com.bigsuika.onlineshop.model.CartItem;
import com.bigsuika.onlineshop.model.User;
import com.bigsuika.onlineshop.service.CartService;
import com.bigsuika.onlineshop.service.RedisCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/cart")
@Tag(name = "Cart API")
public class CartController {
//    private final CartService cartService;
    private final RedisCartService cartService;

//    public CartController(CartService cartService) {
//        this.cartService = cartService;
//    }

    public CartController(RedisCartService cartService) {
        this.cartService = cartService;
    }

    // get current user's cart
    @Operation(summary = "Get current user's cart")
    @GetMapping
    public Map<String, CartItem> getCart(@AuthenticationPrincipal User user){
//        return cartService.getOrCreateCart(user);
        return cartService.getCartItems(user.getId());
    }

    // add product to cart
    @Operation(summary = "Add product to cart")
    @PostMapping("/add")
    public void addToCart(@AuthenticationPrincipal User user,
                          @RequestParam Long productId,
                          @RequestParam(defaultValue = "1") Integer quantity){
        cartService.addItemToCart(user.getId(),productId,quantity);
    }

    // update product quantity
    @Operation(summary = "Update product quantity")
    @PutMapping("/item/{itemId}")
    public void updateItemQuantity(@AuthenticationPrincipal User user,
                                   @PathVariable Long itemId,
                                   @RequestParam Integer quantity) {
        cartService.updateItemQuantity(user.getId(),itemId,quantity);
    }

    // delete product from cart
    @Operation(summary = "Delete a product")
    @DeleteMapping("/item/{itemId}")
    public void removeItem(@AuthenticationPrincipal User user,
                           @PathVariable Long itemId) {
        cartService.removeItemFromCart(user.getId(),itemId);
    }

    // empty the cart
    @Operation(summary = "Delete all products")
    @DeleteMapping("/clear")
    public void clearCart(@AuthenticationPrincipal User user){
        cartService.clearCart(user.getId());
    }
}
