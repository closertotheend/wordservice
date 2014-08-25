package com.wordservice.mvc.util;


public class CleanUtil {
    public static String clean(String word) {
        return word.replaceAll(":","").trim();
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


    public static String removeSpecialCharacters(String word) {
        String noSpecialChars = word.replaceAll("[—“”’\"]", "");
        return noSpecialChars.replaceAll("[\\t\\n\\r]", " ");
    }

    public static String prepareTextForSave(String unparsedText){
        String trimmed = unparsedText.trim();
        String noSpecialNumbers = removeSpecialCharacters(trimmed);
        return noSpecialNumbers.trim();
    }

}
