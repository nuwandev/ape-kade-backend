package com.nuwandev.pos.util;

import org.jspecify.annotations.NonNull;

import java.util.Random;

public class Util {

    private Util() {
    }

    private static Random random = new Random();

    public static @NonNull String generateCustomerId() {
        String prefix = "C";
        int randomNum = random.nextInt(10000);
        return prefix + String.format("%04d", randomNum);
    }
}
