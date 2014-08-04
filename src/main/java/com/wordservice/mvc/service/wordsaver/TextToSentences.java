package com.wordservice.mvc.service.wordsaver;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class TextToSentences {
    private static final BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);

    public static List<String> transform(String text) {
        iterator.setText(text);
        List<String> sentences = new ArrayList<>();

        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            sentences.add(text.substring(start, end).trim());
        }

        return sentences;
    }

}
