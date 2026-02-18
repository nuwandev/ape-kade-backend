package com.nuwandev.pos.util;

import org.jspecify.annotations.Nullable;

public class Util {
    public static @Nullable String generateCustomerId() {
        String prefix = "C";
        int randomNum = (int) (Math.random() * 10000);
        return prefix + String.format("%04d", randomNum);
    }
}
