package com.wordservice.mvc.model;

import com.wordservice.mvc.IntegrationTestsBase;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class SentenceIT extends IntegrationTestsBase {

    @Test
    @Rollback
    public void testGetWordRelationships() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja!");

        assertEquals(2, wordRepository.count());
        assertEquals(1, sentenceRepository.count());
    }
}
