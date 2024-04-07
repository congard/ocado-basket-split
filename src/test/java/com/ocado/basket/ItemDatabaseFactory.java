package com.ocado.basket;

import java.io.IOException;

public class ItemDatabaseFactory {
    private ItemDatabaseFactory() {
        // empty
    }

    public static ItemDatabase create(String resourcePath) {
        try {
            return new ItemDatabase(ResourceHelper.getPath(resourcePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
