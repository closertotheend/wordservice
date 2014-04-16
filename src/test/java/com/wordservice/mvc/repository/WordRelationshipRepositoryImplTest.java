package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

public class WordRelationshipRepositoryImplTest extends IntegrationTestsBase {

    @Test
    public void testFindByWord() throws Exception {
        WordEntity hello = new WordEntity("Hello");
        WordEntity ilja = new WordEntity("Ilja");

        wordRepositoryImpl.save(hello);
        wordRepositoryImpl.save(ilja);

        WordRelationship relationship = new WordRelationship(hello, ilja);
        wordRelationshipRepository.save(relationship);

        assertNotNull(wordRelationshipRepository.getRelationshipBetween(hello, ilja));
    }

}
