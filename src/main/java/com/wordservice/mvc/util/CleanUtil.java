package com.wordservice.mvc.util;

public class CleanUtil {
    public static String clean(String word) {
        return word.replaceAll("/[^A-Za-z0-9 ]/", "").trim();
    }
}
