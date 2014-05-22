package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.IntegrationTestsBase;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.Assert.*;

public class SentenceContextWordFinderServiceIT extends IntegrationTestsBase {

    @Test
    @Rollback
    public void testGetNextWords() throws Exception {
        wordEntitySaverService.saveToRepo("This is text. This is cat. This string should be easy. This is.");
        List<String> sentences = sentenceContextWordFinderService.getNextWords("This", "is");
        assertTrue(sentences.contains("text"));
        assertTrue(sentences.contains("cat"));
        assertEquals(2,sentences.size());
    }

    @Test
    @Rollback
    public void testGetNextWords2() throws Exception {
        wordEntitySaverService.saveToRepo("This is text. This is cat. This string should be easy. This is.");
        List<String> sentences = sentenceContextWordFinderService.getNextWords("This", "iasdaasdasds");
        assertEquals(0,sentences.size());
    }
}