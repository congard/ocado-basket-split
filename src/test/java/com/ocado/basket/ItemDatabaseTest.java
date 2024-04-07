package com.ocado.basket;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemDatabaseTest {

    @Test
    void getItems() {
        var db = SimpleConfigData.getDatabase();
        var actual = new HashSet<>(db.getItems());
        var expected = Set.of("Carrots (1kg)", "Cold Beer (330ml)", "Steak (300g)",
                "AA Battery (4 Pcs.)", "Espresso Machine", "Garden Chair");
        assertEquals(expected, actual);
    }

    @Test
    void getDeliveryMethods() {
        var db = SimpleConfigData.getDatabase();
        var actual = new HashSet<>(db.getDeliveryMethods());
        var expected = Set.of("Express Delivery", "Click&Collect", "Courier");
        assertEquals(expected, actual);
    }

    @Test
    void getItemDeliveryMethods() {
        var db = SimpleConfigData.getDatabase();
        var actual = db.getItemDeliveryMethods(db.getItemId("Carrots (1kg)").orElseThrow()).stream()
                .map(db::getDeliveryMethodName).collect(Collectors.toUnmodifiableSet());
        var expected = Set.of("Express Delivery", "Click&Collect");
        assertEquals(expected, actual);
    }
}