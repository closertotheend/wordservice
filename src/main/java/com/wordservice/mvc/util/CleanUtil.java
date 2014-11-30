package com.wordservice.mvc.util;


public class CleanUtil {
    public static String clean(String word) {
        return word.replaceAll(":","").trim();
    }

    static String removeSpecialCharacters(String word) {
        String noSpecialChars = word.replaceAll("[—“”’\"]", "");
        return noSpecialChars.replaceAll("[\\t\\n\\r]", " ");
    }

    public static String prepareTextForSave(String unparsedText){
        String trimmed = unparsedText.trim();
        String noSpecialNumbers = removeSpecialCharacters(trimmed);
        return noSpecialNumbers.trim();
    }

}
