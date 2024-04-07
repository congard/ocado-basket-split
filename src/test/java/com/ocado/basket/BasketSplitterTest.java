package com.ocado.basket;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BasketSplitterTest {

    @Test
    void splitSimpleConfig() {
        var db = SimpleConfigData.getDatabase();
        var basket = SimpleConfigData.getBasket();
        var splitter = new BasketSplitter(db);
        var actual = splitter.split(basket);
        var expected = Map.of(
            "Express Delivery", List.of("Steak (300g)", "Carrots (1kg)", "Cold Beer (330ml)", "AA Battery (4 Pcs.)"),
            "Courier", List.of("Espresso Machine", "Garden Chair"));

        assertEquals(expected.keySet(), actual.keySet());

        assertEquals(expected.values().stream().map(HashSet::new).collect(Collectors.toSet()),
                actual.values().stream().map(HashSet::new).collect(Collectors.toSet()));
    }
}