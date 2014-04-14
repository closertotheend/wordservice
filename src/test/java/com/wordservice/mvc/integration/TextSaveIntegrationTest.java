package com.wordservice.mvc.integration;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.junit.Test;

import static junit.framework.Assert.*;

public class TextSaveIntegrationTest extends IntegrationTestsBase {


    @Test
    public void persistedTextShouldBeRetrievable() {
        wordEntitySaverService.saveToRepo(dickensText);
        assertTrue(wordRepository.count() > 300);
        assertNull(template.getRelationshipBetween(new WordEntity("zxc"), new WordEntity("ds"), WordRelationship.class, WordRelationship.relationshipType));
    }

    @Test
    public void checkLastName() {
        wordEntitySaverService.saveToRepo("Hello Ilja!");
        assertTrue(wordRepository.count() == 2);
    }

    @Test
    public void checkMultipleSenences() {
        wordEntitySaverService.saveToRepo("Hello Ilja! My name is neo4j, and I am confused.");
        assertTrue(wordRepository.count() == 10);
    }

    @Test
    public void getAllWordsAfterA() {
        wordEntitySaverService.saveToRepo("tralala hahaha.");
        long startTime = System.currentTimeMillis();
        wordEntitySaverService.saveToRepo(dickensText);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.err.println("All opertaions " + estimatedTime);
        WordEntity byPropertyValue = wordRepository.findByWord("a");
        //System.err.println(byPropertyValue.getFollowedAfterWords());
    }

    @Test
    public void testRelationshipCreatement() {
        wordEntitySaverService.saveToRepo("Hello Ilja, I am neo4j, I am slow and ugly! I am happy, I am happy.");
        assertEquals(3,wordRepository.findByWord("am").getRelationships().size());
        WordRelationship relationshipBetweenAmAndHappy = template.getRelationshipBetween(
                wordRepository.findByWord("am"), wordRepository.findByWord("happy"),
                WordRelationship.class, WordRelationship.relationshipType);
        assertEquals(1, relationshipBetweenAmAndHappy.getPopularity());
    }

    @Test
    public void checkInjection() {
        assertNotNull(wordEntitySaverService);
        assertNotNull(template);
        assertNotNull(wordRepository);
    }


}