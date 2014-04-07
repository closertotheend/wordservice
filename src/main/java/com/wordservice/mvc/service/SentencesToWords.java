package com.wordservice.mvc.service;

import com.wordservice.mvc.model.WordEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ilja on 4/6/2014.
 */
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
            cleanWords.add(cleanWord(dirtyWord));
        }
        return cleanWords;
    }

    private static List<String> splitSenteceToWords(String sentence) {
        return Arrays.asList(sentence.split(" "));
    }

    static String cleanWord(String dirtyWord) {
        return dirtyWord.replaceAll("[^\\p{L}\\p{Nd}]", "");
    }

}
