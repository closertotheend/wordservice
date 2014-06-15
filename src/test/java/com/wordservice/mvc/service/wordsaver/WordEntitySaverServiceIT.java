package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static junit.framework.Assert.*;

public class WordEntitySaverServiceIT extends IntegrationTestsBase {


    @Test
    @Ignore
    public void shouldSaveBigAmountOfWordsWithoutFailing() {
        long startTime = System.currentTimeMillis();

        wordEntitySaverService.saveToRepo(dickensText);
        assertTrue(wordRepository.count() > 300);
        assertNull(template.getRelationshipBetween(new WordEntity("zxc"), new WordEntity("ds"), WordRelationship.class, WordRelationship.relationshipType));

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.err.println(estimatedTime);
    }

    @Test
    @Rollback
    public void shouldReturnCorrectAmountOfWords() {
        wordEntitySaverService.saveToRepo("Hello Ilja!");
        assertEquals(2, wordRepository.count());
    }

    @Test
    @Rollback
    public void shouldReturnCorrectPopularityOfSavedWord() {
        wordEntitySaverService.saveToRepo("Hello Hello Hello");
        assertEquals(2, wordRepositoryFixedIndexesSearch.findByWord("Hello").getPopularity());
    }

    @Test
    @Rollback
    public void shouldSaveWordsFromDifferentSenteces() {
        wordEntitySaverService.saveToRepo("Hello Ilja! My name is neo4j, and I am confused.");
        assertTrue(wordRepository.count() == 10);
    }

    @Test
    @Rollback
    public void shouldCheckThatSavedWordsShouldBeRetrivable() {
        wordEntitySaverService.saveToRepo("tralala hahaha.");
        WordEntity hahaha = wordRepositoryFixedIndexesSearch.findByWord("hahaha");
        assertNotNull(hahaha);
        WordEntity a = wordRepositoryFixedIndexesSearch.findByWord("a");
        assertNull(a);
    }

    @Test
    @Rollback
    public void shouldCreateCorrectAmountOfRelationshipsAndCorrectlyIncrementPopularityOfThem() {
        wordEntitySaverService.saveToRepo("Hello Ilja, I am neo4j, I am slow and ugly! I am happy, I am happy.");
        WordRelationship relationshipBetweenAmAndHappy = template.getRelationshipBetween(
                wordRepositoryFixedIndexesSearch.findByWord("am"), wordRepositoryFixedIndexesSearch.findByWord("happy"),
                WordRelationship.class, WordRelationship.relationshipType);
        assertEquals(1, relationshipBetweenAmAndHappy.getPopularity());
    }

    @Test
    @Rollback
    public void checkInjection() {
        assertNotNull(wordEntitySaverService);
        assertNotNull(template);
        assertNotNull(wordRepository);
    }


}