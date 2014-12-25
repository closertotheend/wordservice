package com.wordservice.mvc.bdd;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.*;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static junit.framework.Assert.*;
import static junit.framework.Assert.assertNotNull;

public class TextSaverServiceIT extends IntegrationTestsBase {


    @Test
    @Rollback
    @Ignore
    public void shouldSaveBigAmountOfWordsWithoutFailing() {
        long startTime = System.currentTimeMillis();

        textSaverService.save(dickensText);
        assertTrue(wordEntityRepository.count() > 300);

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.err.println(estimatedTime);
    }

    @Test
    @Rollback
    public void shouldReturnCorrectAmountOfWords() {
        textSaverService.save("Hello Ilja!");
        assertEquals(2, wordEntityRepository.count());
    }

    @Test
    @Rollback
    public void shouldSaveOneWord() {
        textSaverService.save("Hello");
        assertEquals(1, wordEntityRepository.count());
    }

    @Test
    @Rollback
    public void shouldReturnCorrectPopularityOfSavedWord() {
        textSaverService.save("Hello Hello Hello");
        assertEquals(2, wordEntityDAO.findByWordViaIndexAndRegex("Hello").getPopularity());
    }

    @Test
    @Rollback
    public void shouldSaveWordsFromDifferentSenteces() {
        textSaverService.save("Hello Ilja! My name is neo4j, and I am confused.");
        assertTrue(wordEntityRepository.count() == 10);
    }

    @Test
    @Rollback
    public void shouldCheckThatSavedWordsShouldBeRetrivable() {
        textSaverService.save("tralala hahaha.");
        WordEntity hahaha = wordEntityDAO.findByWordViaIndexAndRegex("hahaha");
        assertNotNull(hahaha);
        WordEntity a = wordEntityDAO.findByWordViaIndexAndRegex("a");
        assertNull(a);
    }





    @Test
    @Rollback
    public void checkInjection() {
        assertNotNull(textSaverService);
        assertNotNull(template);
        assertNotNull(wordEntityRepository);
    }


}