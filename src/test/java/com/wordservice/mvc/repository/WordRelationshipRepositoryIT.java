package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

public class WordRelationshipRepositoryIT extends IntegrationTestsBase {

    @Test
    @Rollback
    public void testSave() throws Exception {
        WordEntity hello = new WordEntity("Hello");
        WordEntity ilja = new WordEntity("Ilja");

        wordRepositoryImpl.save(hello);
        wordRepositoryImpl.save(ilja);

        WordRelationship relationship = new WordRelationship(hello, ilja);
        wordRelationshipRepository.save(relationship);

        Iterator<WordRelationship> iterator = wordRelationshipRepository.findAll().iterator();
        assertNotNull(iterator.next());
        try {
            iterator.next();
            fail();
        }catch (NoSuchElementException e){

        }
    }

    @Test
    @Rollback
    public void testGetRelationshipBetween() throws Exception {
        WordEntity hello = new WordEntity("Hello");
        WordEntity ilja = new WordEntity("Ilja");

        wordRepositoryImpl.save(hello);
        wordRepositoryImpl.save(ilja);

        WordRelationship relationship = new WordRelationship(hello, ilja);
        wordRelationshipRepository.save(relationship);

        assertNotNull(wordRelationshipRepository.getRelationshipBetween(hello, ilja));
    }

}
