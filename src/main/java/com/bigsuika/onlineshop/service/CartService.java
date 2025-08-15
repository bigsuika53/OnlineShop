package com.bigsuika.onlineshop.service;

import com.bigsuika.onlineshop.model.Cart;
import com.bigsuika.onlineshop.model.CartItem;
import com.bigsuika.onlineshop.model.Product;
import com.bigsuika.onlineshop.model.User;
import com.bigsuika.onlineshop.repository.CartItemRepository;
import com.bigsuika.onlineshop.repository.CartRepository;
import com.bigsuika.onlineshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    // get user's cart or create a new cart
    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    // add products to the cart
    public Cart addItemToCart(User user, Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product not found"));

        Cart cart = getOrCreateCart(user);

        // check if the product already in cart or update the quantity
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElse(null);

        if (cartItem != null) {
            // update quantity
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            // add new product
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            cartItemRepository.save(newCartItem);
            cart.getItems().add(newCartItem);
        }

        return cartRepository.save(cart);
    }

    // update the product's quantity
    public Cart updateItemQuantity(User user, Long itemId, Integer quantity){
        Cart cart = getOrCreateCart(user);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("cart item not found"));

        // verify if the item belongs to current user
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("unable to update this cart item");
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return cartRepository.save(cart);
    }

    // remove product from cart
    public Cart removeItemFromCart(User user, Long itemId) {
        Cart cart = getOrCreateCart(user);
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("item not found"));

        // verify if the item belongs to current user
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("unable to delete this cart item");
        }

        cartItemRepository.delete(item);
        cart.getItems().remove(item);
        return cartRepository.save(cart);
    }

    // empty the cart
    public void clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        cartItemRepository.deleteByCartId(cart.getId());
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
