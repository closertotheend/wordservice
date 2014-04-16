package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by ilja on 4/7/2014.
 */

public class WordRepositoryImplTest extends IntegrationTestsBase{


    @Test
    public void testFindByWord() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja!");
        assertNotNull(wordRepositoryImpl.findByWord("Hello"));
    }

    @Test
    public void testGetTop10Words() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja, I am neo4j, I am slow, I am slow, I am slow and ugly! I am happy, I am happy.");
        assertNotNull(wordRepositoryImpl.getTop10WordsAfter("am"));
        System.err.println(wordRepositoryImpl.getTop10WordsAfter("am"));
        System.err.println(wordRepositoryImpl.getTop10WordsAfter("I","am"));
    }
}
