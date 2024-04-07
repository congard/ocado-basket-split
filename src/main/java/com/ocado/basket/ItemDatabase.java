package com.ocado.basket;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Stores items & delivery methods.
 * Each item and delivery method is represented by id.
 */
public class ItemDatabase {
    private final List<String> items;
    private final List<String> deliveryMethods;

    // Map<ItemId, List<DeliveryMethodId>>
    private final Map<Integer, List<Integer>> itemDeliveries = new HashMap<>();

    public ItemDatabase(@NotNull Path configPath) throws IOException {
        this(readJSONObject(configPath));
    }

    public ItemDatabase(@NotNull JSONObject itemsJson) {
        // delivery method name -> id
        var deliveryMethodsMap = new HashMap<String, Integer>();

        items = new ArrayList<>(itemsJson.length());

        itemsJson.keys().forEachRemaining(product -> {
            var productId = items.size();
            items.add(product);
            itemDeliveries.put(productId, new ArrayList<>());

            var itemMethods = itemDeliveries.get(productId);
            var itemMethodsJson = itemsJson.getJSONArray(product);

            for (int i = 0; i < itemMethodsJson.length(); ++i) {
                var method = itemMethodsJson.getString(i);
                var methodId = deliveryMethodsMap.computeIfAbsent(method, k -> deliveryMethodsMap.size());
                itemMethods.add(methodId);
            }
        });

        deliveryMethods = new ArrayList<>(Collections.nCopies(deliveryMethodsMap.size(), null));
        deliveryMethodsMap.forEach((k, v) -> deliveryMethods.set(v, k));
    }

    public List<String> getItems() {
        return items;
    }

    public List<String> getDeliveryMethods() {
        return deliveryMethods;
    }

    public List<Integer> getItemDeliveryMethods(int itemId) {
        return itemDeliveries.get(itemId);
    }

    public Optional<Integer> getItemId(@NotNull String name) {
        var index = items.indexOf(name);
        return index != -1 ? Optional.of(index) : Optional.empty();
    }

    public Optional<Integer> getDeliveryMethodId(@NotNull String name) {
        var index = deliveryMethods.indexOf(name);
        return index != -1 ? Optional.of(index) : Optional.empty();
    }

    public String getItemName(int id) {
        return id < items.size() ? items.get(id) : null;
    }

    public String getDeliveryMethodName(int id) {
        return id < deliveryMethods.size() ? deliveryMethods.get(id) : null;
    }

    private static JSONObject readJSONObject(Path path) throws IOException {
        if (!Files.exists(path))
            throw new FileNotFoundException("File " + path + " cannot be found");
        return new JSONObject(Files.readString(path));
    }
}
