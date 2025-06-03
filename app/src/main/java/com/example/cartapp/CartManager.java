package com.example.cartapp;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product item) {
        for (CartItem i : cartItems) {
            if (i.getName().equals(item.getName())) {
                // Increase quantity if same item
                cartItems.set(cartItems.indexOf(i), new CartItem(i.getName(), i.getPrice(), i.getQuantity() + 1));
                return;
            }
        }
        cartItems.add(new CartItem(item.getName(), item.getPrice(), 1));
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
