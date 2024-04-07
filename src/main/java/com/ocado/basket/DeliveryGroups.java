package com.ocado.basket;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Represents delivery groups of the specified items.
 * Each item can belong to multiple delivery groups.
 */
public class DeliveryGroups {
    // <DeliveryMethodId, DeliveryGroup>
    private final Map<Integer, DeliveryGroup> deliveryGroups = new HashMap<>();
    private final ItemDatabase db;
    private final int itemCount;

    public DeliveryGroups(@NotNull ItemDatabase db, @NotNull List<Integer> itemIds) {
        this.db = db;
        itemCount = itemIds.size();

        itemIds.forEach(itemId -> db.getItemDeliveryMethods(itemId).forEach(deliveryMethodId ->
                deliveryGroups.computeIfAbsent(deliveryMethodId, k -> new DeliveryGroup(deliveryMethodId)).addItem(itemId)));
    }

    public DeliveryGroup getGroup(int id) {
        return deliveryGroups.get(id);
    }

    public Optional<DeliveryGroup> getGroupByName(@NotNull String name) {
        return db.getDeliveryMethodId(name).map(deliveryGroups::get);
    }

    public List<DeliveryGroup> getGroups() {
        return deliveryGroups.values().stream().toList();
    }

    public Set<Integer> getIds() {
        return deliveryGroups.keySet();
    }

    public int size() {
        return deliveryGroups.size();
    }

    public boolean isFull(@NotNull List<DeliveryGroup> groups) {
        return DeliveryGroup.countItems(groups) == itemCount;
    }

    @Override
    public String toString() {
        return "DeliveryGroups{" +
                "deliveryGroups=" + deliveryGroups +
                '}';
    }
}
