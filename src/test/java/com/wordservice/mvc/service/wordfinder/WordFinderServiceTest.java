package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.IntegrationTestsBase;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.Assert.*;

public class WordFinderServiceTest extends IntegrationTestsBase {

    @Test
    @Rollback
    public void testGetNextWords() throws Exception {
        wordEntitySaverService.saveToRepo("This is text. This is cat. This string should be easy. This is.");
        List<String> sentences = wordFinderService.getNextWords("This", "is");
        assertEquals("text", sentences.get(0));
        assertEquals("cat", sentences.get(1));
        assertEquals(2,sentences.size());
    }

    @Test
    @Rollback
    public void testGetNextWords2() throws Exception {
        wordEntitySaverService.saveToRepo("This is text. This is cat. This string should be easy. This is.");
        List<String> sentences = wordFinderService.getNextWords("This", "iasdaasdasds");
        assertEquals(0,sentences.size());
    }
}