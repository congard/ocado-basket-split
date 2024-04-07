package com.ocado.basket;

import org.json.JSONArray;

import java.util.List;

public class SimpleConfigData {
    private static final List<String> basket =
            new JSONArray(ResourceHelper.readString("simple_config_basket.json")).toList().stream()
                    .map(Object::toString).toList();

    private static final ItemDatabase db = ItemDatabaseFactory.create("simple_config.json");

    public static List<String> getBasket() {
        return basket;
    }

    public static ItemDatabase getDatabase() {
        return db;
    }
}
