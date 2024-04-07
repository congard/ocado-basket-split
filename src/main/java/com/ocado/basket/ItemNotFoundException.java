package com.ocado.basket;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(String itemName) {
        super("Item '" + itemName + "' cannot be found");
    }

    public ItemNotFoundException() {
        super("Item cannot be found");
    }
}
