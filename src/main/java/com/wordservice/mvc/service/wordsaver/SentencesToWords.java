package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.util.CleanUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SentencesToWords {

    public static List<String> transform(String sentence) {
        ArrayList<String> wordEntities = new ArrayList<>();
        for (String word : splitSentenceToCleanWords(sentence)) {
            wordEntities.add(word);
        }
        return wordEntities;
    }

    private static List<String> splitSentenceToCleanWords(String sentence) {
        List<String> cleanWords = new ArrayList<>();
        for (String dirtyWord : splitSenteceToWords(sentence)) {
            String cleanWord = cleanWord(dirtyWord);
            if(!cleanWord.isEmpty()){
                cleanWords.add(cleanWord);
            }
        }
        return cleanWords;
    }

    private static List<String> splitSenteceToWords(String sentence) {
        return Arrays.asList(sentence.split(" "));
    }

    static String cleanWord(String dirtyWord) {
        String wordToBeCleaned = dirtyWord.trim();
        wordToBeCleaned = cleanFromLeft(wordToBeCleaned);
        wordToBeCleaned = cleanFromRight(wordToBeCleaned);
        return wordToBeCleaned;
    }

    private static String cleanFromLeft(String wordToBeCleaned) {
        if (wordToBeCleaned.length()>0 &&
            !Character.isLetter(wordToBeCleaned.charAt(0)) &&
            !Character.isDigit(wordToBeCleaned.charAt(0))) {
            wordToBeCleaned = "" + wordToBeCleaned.substring(1);
            wordToBeCleaned = cleanFromLeft(wordToBeCleaned);
        }
        return wordToBeCleaned;
    }

    private static String cleanFromRight(String wordToBeCleaned) {
        if (wordToBeCleaned.length()>0 &&
            !Character.isLetter(wordToBeCleaned.charAt(wordToBeCleaned.length() - 1)) &&
            !Character.isDigit(wordToBeCleaned.charAt(wordToBeCleaned.length() - 1))) {
            wordToBeCleaned = wordToBeCleaned.substring(0, wordToBeCleaned.length() - 1) + "";
            wordToBeCleaned = cleanFromRight(wordToBeCleaned);
        }
        return wordToBeCleaned;
    }


}
