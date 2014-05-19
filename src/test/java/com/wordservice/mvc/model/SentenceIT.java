package com.wordservice.mvc.model;

import com.wordservice.mvc.IntegrationTestsBase;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class SentenceIT extends IntegrationTestsBase {

    @Test
    @Rollback
    public void testGetWordRelationships() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja!");

        assertEquals(2,wordRepositoryImpl.count());
        assertEquals(1, sentenceRepository.count());
    }
}
