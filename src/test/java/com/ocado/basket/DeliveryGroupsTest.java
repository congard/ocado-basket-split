package com.ocado.basket;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryGroupsTest {

    @Test
    void getIds() {
        var db = SimpleConfigData.getDatabase();
        var groups = createDeliveryGroups(db, SimpleConfigData.getBasket());
        var expected = IntStream.range(0, 3).boxed().collect(Collectors.toSet());
        var actual = new HashSet<>(groups.getIds());
        assertEquals(expected, actual);
    }

    @Test
    void isFull() {
        var db = SimpleConfigData.getDatabase();
        var groups = createDeliveryGroups(db, SimpleConfigData.getBasket());

        assertTrue(groups.isFull(groups.getGroups()));

        assertTrue(groups.isFull(List.of(
                groups.getGroupByName("Express Delivery").orElseThrow(),
                groups.getGroupByName("Courier").orElseThrow())));

        assertFalse(groups.isFull(List.of(
                groups.getGroupByName("Express Delivery").orElseThrow(),
                groups.getGroupByName("Click&Collect").orElseThrow())));
    }

    private DeliveryGroups createDeliveryGroups(ItemDatabase db, List<String> items) {
        return new DeliveryGroups(db, items.stream()
                .map(name -> db.getItemId(name).orElseThrow(() -> new ItemNotFoundException(name))).toList());
    }
}