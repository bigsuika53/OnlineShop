package com.bigsuika.onlineshop.service;

import com.bigsuika.onlineshop.model.CartItem;
import com.bigsuika.onlineshop.model.Product;
import com.bigsuika.onlineshop.repository.ProductRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RedisCartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, CartItem> hashOperations;
    private final ProductRepository productRepository;

    private static final String CART_KEY_PREFIX = "cart:";

    public RedisCartService(ProductRepository productRepository,
                            RedisTemplate<String, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    private String getCartKey(Long userId) {
        return CART_KEY_PREFIX + userId;
    }

    // add product to cart
    public void addItemToCart(Long userId, Long productId, Integer quantity) {
        String cartKey = getCartKey(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product not found"));
        CartItem item = hashOperations.get(cartKey, productId.toString());
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
        }
        hashOperations.put(cartKey, productId.toString(), item);
    }

    // get the cart
    public Map<String, CartItem> getCartItems(Long userId) {
        String cartKey = getCartKey(userId);
        return hashOperations.entries(cartKey);
    }

    // update product quantity
    public void updateItemQuantity(Long userId, Long productId, Integer quantity) {
        String cartKey = getCartKey(userId);
        CartItem item = hashOperations.get(cartKey, productId.toString());
        if (item != null) {
            item.setQuantity(quantity);
            hashOperations.put(cartKey, productId.toString(), item);
        }
    }

    // delete product from cart
    public void removeItemFromCart(Long userId, Long productId) {
        String cartKey = getCartKey(userId);
        hashOperations.delete(cartKey, productId.toString());
    }

    // empty the cart
    public void clearCart(Long userId) {
        String cartKey = getCartKey(userId);
        hashOperations.getOperations().delete(cartKey);
    }
}
