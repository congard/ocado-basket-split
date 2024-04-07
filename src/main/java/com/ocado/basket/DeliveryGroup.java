package com.ocado.basket;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Represents a single delivery group.
 */
public class DeliveryGroup {
    private final int id;
    private final Set<Integer> items = new HashSet<>();

    public DeliveryGroup(int id) {
        this.id = id;
    }

    public void addItem(int itemId) {
        items.add(itemId);
    }

    public Set<Integer> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public int size() {
        return items.size();
    }

    @Override
    public String toString() {
        return "DeliveryGroup{" +
                "id=" + id +
                ", items=" + items +
                '}';
    }

    /**
     * Counts items in the specified list of groups.
     * The same item in multiple groups counts as 1.
     * @param groups The list of groups.
     * @return The total count of items.
     */
    public static int countItems(@NotNull List<DeliveryGroup> groups) {
        // memory complexity: O(n), n - item count
         var set = new HashSet<Integer>();
         groups.forEach(deliveryGroup -> set.addAll(deliveryGroup.getItems()));
         return set.size();
    }

    private static Integer toInt(Integer i) {
        return i == null ? 0 : i;
    }
}
