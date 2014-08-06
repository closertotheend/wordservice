package com.wordservice.mvc.util;

public class CleanUtil {
    public static String clean(String word) {

        return word.replaceAll("/[^A-Za-z0-9 ]/", "").trim();
    }


    public static boolean hasNonWordCharacter(String s) {
        char[] a = s.toCharArray();
        for (char c : a) {
            if (!Character.isLetter(c)) {
                return true;
            }
        }

        return false;
    }

}
