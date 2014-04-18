package com.wordservice.mvc.integration;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.junit.Test;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertEquals;

public class TextSaveIntegrationTest extends IntegrationTestsBase {


    @Test
    public void persistedTextShouldBeRetrievable() {
        wordEntitySaverService.saveToRepo(dickensText);
        assertTrue(wordRepositoryImpl.count() > 300);
        assertNull(template.getRelationshipBetween(new WordEntity("zxc"), new WordEntity("ds"), WordRelationship.class, WordRelationship.relationshipType));
    }

    @Test
    public void checkWordCount() {
        wordEntitySaverService.saveToRepo("Hello Ilja!");
        assertEquals(2,wordRepositoryImpl.count());
    }

    @Test
    public void checkLastName() {
        wordEntitySaverService.saveToRepo("Hello Hello Hello");
        assertEquals(2, wordRepositoryImpl.findByWord("Hello").getPopularity());
    }

    @Test
    public void checkMultipleSenences() {
        wordEntitySaverService.saveToRepo("Hello Ilja! My name is neo4j, and I am confused.");
        assertTrue(wordRepositoryImpl.count() == 10);
    }

    @Test
    public void getAllWordsAfterA() {
        wordEntitySaverService.saveToRepo("tralala hahaha.");
        long startTime = System.currentTimeMillis();
        wordEntitySaverService.saveToRepo(dickensText);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.err.println("All opertaions " + estimatedTime);
        WordEntity byPropertyValue = wordRepositoryImpl.findByWord("a");
    }

    @Test
    public void testRelationshipCreatement() {
        wordEntitySaverService.saveToRepo("Hello Ilja, I am neo4j, I am slow and ugly! I am happy, I am happy.");
        WordRelationship relationshipBetweenAmAndHappy = template.getRelationshipBetween(
                wordRepositoryImpl.findByWord("am"), wordRepositoryImpl.findByWord("happy"),
                WordRelationship.class, WordRelationship.relationshipType);
        assertEquals(1, relationshipBetweenAmAndHappy.getPopularity());
    }

    @Test
    public void checkInjection() {
        assertNotNull(wordEntitySaverService);
        assertNotNull(template);
        assertNotNull(wordRepositoryImpl);
    }


}