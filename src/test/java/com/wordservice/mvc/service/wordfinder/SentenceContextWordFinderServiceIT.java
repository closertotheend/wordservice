package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SentenceContextWordFinderServiceIT extends IntegrationTestsBase {

    @Test
    @Rollback
    public void testGetNextWords() throws Exception {
        textSaverService.saveToRepo("This is text. This is cat. This string should be easy. This is.");
        List<WordEntity> sentences = sentenceContextWordFinderService.getNextWords("This", "is");
        assertTrue(sentences.contains(wordRepositoryFixedIndexesSearch.findByWord("text")));
        assertTrue(sentences.contains(wordRepositoryFixedIndexesSearch.findByWord("cat")));
        assertEquals(2,sentences.size());
    }

    @Test
    @Rollback
    public void testGetNextWords2() throws Exception {
        textSaverService.saveToRepo("This is text. This is cat. This string should be easy. This is.");
        List<WordEntity> sentences = sentenceContextWordFinderService.getNextWords("This", "iasdaasdasds");
        assertEquals(0,sentences.size());
    }
}