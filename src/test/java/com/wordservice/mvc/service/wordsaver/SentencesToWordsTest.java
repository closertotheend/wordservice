package com.wordservice.mvc.service.wordsaver;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SentencesToWordsTest {
    @Test
    public void testTransform() throws Exception {
        List<String> oneSentenceList = new ArrayList<>();
        String sentence = "This is sentence";
        oneSentenceList.add(sentence);

        ArrayList<String> wordEntities = new ArrayList<>();
        wordEntities.add("This");
        wordEntities.add("is");
        wordEntities.add("sentence");
        assertEquals("check that split of th word works fine",wordEntities, SentencesToWords.transform("This is sentence"));
    }

    @Test
    public void testCleanWord() throws Exception {
        assertEquals("Badword", SentencesToWords.cleanWord("Badword."));
        assertEquals("it's", SentencesToWords.cleanWord("it's"));
        assertEquals("Badword", SentencesToWords.cleanWord("Badword?"));
        assertEquals("Badword", SentencesToWords.cleanWord("Badword!"));
        assertEquals("Badword", SentencesToWords.cleanWord("Badword-"));
        assertEquals("Badword", SentencesToWords.cleanWord("Badword,-"));
        assertEquals("üüüüööö", SentencesToWords.cleanWord("üüüüööö"));
        assertEquals("Эйты", SentencesToWords.cleanWord("-Эйты!"));

    }
}
