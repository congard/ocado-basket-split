package com.ocado.basket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("""
                    Invalid arguments.
                        <arg0>    path to the config file
                        <arg1>    path to the basket file""");
            return;
        }

        var configPath = args[0];
        var basketPath = args[1];

        var basket = new JSONArray(Files.readString(Path.of(basketPath)))
                .toList().stream()
                .map(Object::toString)
                .toList();

        System.out.println(new JSONObject(new BasketSplitter(configPath).split(basket)));
    }
}