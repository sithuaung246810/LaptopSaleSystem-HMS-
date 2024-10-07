package com.laptopsale.dto;

import java.util.ArrayList;
import java.util.List;

public class CartItemListDTO {
    private List<CartItemDTO> cartItems;

    public CartItemListDTO() {
        this.cartItems = new ArrayList<>();
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
