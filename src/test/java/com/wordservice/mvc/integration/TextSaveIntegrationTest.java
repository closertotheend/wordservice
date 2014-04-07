package com.wordservice.mvc.integration;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.junit.Test;

import static junit.framework.Assert.*;

public class TextSaveIntegrationTest extends IntegrationTestsBase {


    @Test
    public void persistedTextShouldBeRetrievable() {
        wordEntityService.saveToRepo(dickensText);
        assertTrue(wordRepository.count() > 300);
        assertNull(template.getRelationshipBetween(new WordEntity("zxc"), new WordEntity("ds"), WordRelationship.class, WordRelationship.relationshipType));
    }

    @Test
    public void checkLastName() {
        wordEntityService.saveToRepo("Hello Ilja!");
        assertTrue(wordRepository.count() == 2);
    }

    @Test
    public void checkMultipleSenences() {
        wordEntityService.saveToRepo("Hello Ilja! My name is neo4j, and I am confused.");
        assertTrue(wordRepository.count() == 10);
    }

    @Test
    public void getAllWordsAfterA() {
        wordEntityService.saveToRepo(dickensText);
        long startTime = System.currentTimeMillis();
        WordEntity byPropertyValue = wordRepository.findByWord("a");
        System.err.println(byPropertyValue.getFollowedWords());
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.err.println(estimatedTime);
    }

    @Test
    public void testRelationshipCreatement() {
        wordEntityService.saveToRepo("Hello Ilja, I am neo4j, I am slow and ugly! I am happy, I am happy.");
        assertEquals(3,wordRepository.findByWord("am").getRelationships().size());
        WordRelationship relationshipBetweenAmAndHappy = template.getRelationshipBetween(
                wordRepository.findByWord("am"), wordRepository.findByWord("happy"),
                WordRelationship.class, WordRelationship.relationshipType);
        assertEquals(1, relationshipBetweenAmAndHappy.getPopularity());
    }

    @Test
    public void checkInjection() {
        assertNotNull(wordEntityService);
        assertNotNull(template);
        assertNotNull(wordRepository);
    }


}