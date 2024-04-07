package com.ocado.basket;

import org.jetbrains.annotations.NotNull;
import org.paukov.combinatorics3.Generator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class BasketSplitter {
    private final ItemDatabase db;

    public BasketSplitter(@NotNull String absolutePathToConfigFile) {
        try {
            db = new ItemDatabase(Path.of(absolutePathToConfigFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BasketSplitter(@NotNull ItemDatabase db) {
        this.db = db;
    }

    public Map<String, List<String>> split(@NotNull List<String> items) {
        var deliveryGroups = new DeliveryGroups(db, items.stream()
                .map(name -> db.getItemId(name).orElseThrow(() -> new ItemNotFoundException(name))).toList());

        // the task is to find such combination of delivery groups as:
        // 1. The number of groups is minimal
        // 2. The largest group has the most elements

        // for each combination:
        // 1. Check if all items can be covered
        // 2. Find the largest group
        // 3. Check if the group is the largest one

        var combinationGenerator = Generator.combination(deliveryGroups.getIds());

        for (int groupSize = 1; groupSize <= deliveryGroups.size(); ++groupSize) {
            int largestGroupSize = -1;
            Map<Integer, Set<Integer>> bestResult = null;

            for (var combination : combinationGenerator.simple(groupSize).stream().toList()) {
                var groups = new ArrayList<>(combination.stream()
                        .map(deliveryGroups::getGroup)
                        .sorted((g1, g2) -> Integer.compare(g2.size(), g1.size())) // the simplest way is to sort (nlogn)
                        .toList());

                if (deliveryGroups.isFull(groups)) {
                    var largest = groups.get(0);

                    if (largest.size() <= largestGroupSize)
                        continue;

                    // subtract; leave largest alone
                    // <DeliveryMethodId, List<ItemId>>
                    var processedGroups = new HashMap<Integer, Set<Integer>>();
                    processedGroups.put(largest.getId(), largest.getItems());

                    for (int i = 0; i < groups.size(); ++i) {
                        var groupItemIds = processedGroups.get(groups.get(i).getId());

                        for (int j = i + 1; j < groups.size(); ++j) {
                            var currGroup = groups.get(j);
                            var currGroupItemIds = processedGroups.computeIfAbsent(
                                    currGroup.getId(), k -> new HashSet<>(currGroup.getItems()));
                            currGroupItemIds.removeAll(groupItemIds);
                        }
                    }

                    largestGroupSize = largest.size();
                    bestResult = processedGroups;
                }
            }

            if (largestGroupSize != -1) {
                var result = new HashMap<String, List<String>>();

                for (var x : bestResult.entrySet()) {
                    result.put(db.getDeliveryMethodName(x.getKey()),
                            x.getValue().stream().map(db::getItemName).toList());
                }

                return result;
            }
        }

        return new HashMap<>();
    }
}