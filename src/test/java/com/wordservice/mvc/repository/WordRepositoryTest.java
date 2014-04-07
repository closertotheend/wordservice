package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by ilja on 4/7/2014.
 */

public class WordRepositoryTest extends IntegrationTestsBase{


    @Test
    public void testFindByWord() throws Exception {
        wordEntityService.saveToRepo("Hello Ilja!");
        assertNotNull(wordRepository.findByWord("Hello"));
    }

    @Test
    public void testGetTop10Words() throws Exception {
        wordEntityService.saveToRepo("Hello Ilja, I am neo4j, I am slow, I am slow, I am slow and ugly! I am happy, I am happy.");
        assertNotNull(wordRepository.getTop10WordsAfter("am"));
        System.err.println(wordRepository.getTop10WordsAfter("am"));
    }
}
