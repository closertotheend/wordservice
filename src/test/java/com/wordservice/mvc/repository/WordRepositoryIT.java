package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class WordRepositoryIT extends IntegrationTestsBase {


    @Test
    @Rollback
    public void testFindByWord() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja!");
        assertNotNull(wordRepositoryImpl.findByWord("Hello"));
    }

    @Test
    @Rollback
    public void testGetTop10Words() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja, I am neo4j, I am slow, I am slow, I am slow and ugly! I am happy, I am happy.");
        assertEquals("There is 3 words followed after word am" , 3 ,wordRepositoryImpl.getTop10WordsAfter("am").size());
    }

    @Test
    @Rollback
    public void testGetTop10WordsWith2Arguments() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja, I am neo4j database, I am slow, I am slow, I am slow and ugly! I am happy, I am happy. " +
                "This am does nothing but still is in context");
        assertEquals("There is 4 words followed after words I&am" , 4 ,wordRepositoryImpl.getTop10WordsAfter("I","am").size());
        assertEquals("There is NO words followed after words I&neo4j" , 0 ,wordRepositoryImpl.getTop10WordsAfter("I","neo4j").size());

    }
}
