package com.ocado.basket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceHelper {
    // ignore warning
    private static final Object obj = new ResourceHelper();

    private ResourceHelper() {
        // empty
    }

    public static Path getPath(String resourcePath) {
        try {
            return Paths.get(obj.getClass().getClassLoader().getResource(resourcePath).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String resourcePath) {
        try {
            return Files.readString(getPath(resourcePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
