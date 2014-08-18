package com.wordservice.mvc.util;

import scala.util.parsing.combinator.testing.Str;

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



    public static String removeNumbers(String word) {
        return word.replaceAll("\\d+.*", "");
    }

    public static String removeSpecialCharacters(String word) {
        return word.replaceAll("[—“”’\"\\t\\n\\r]", "");
    }



    public static String prepareTextForSave(String unparsedText){
        String trimmed = unparsedText.trim();
        String noNumbers = removeNumbers(trimmed);
        String noSpecialNumbers = removeSpecialCharacters(noNumbers);
        return noSpecialNumbers.trim();
    }




}
