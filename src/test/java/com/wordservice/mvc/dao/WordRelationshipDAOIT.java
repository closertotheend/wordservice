package com.wordservice.mvc.dao;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

public class WordRelationshipDAOIT extends IntegrationTestsBase {

    @Test
    @Rollback
    public void testSave() throws Exception {
        WordEntity hello = new WordEntity("Hello");
        WordEntity ilja = new WordEntity("Ilja");

        wordEntityRepository.save(hello);
        wordEntityRepository.save(ilja);

        WordRelationship relationship = new WordRelationship(hello, ilja);
        wordRelationshipDAO.save(relationship);

        Iterator<WordRelationship> iterator = wordRelationshipDAO.findAll().iterator();
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

        wordEntityRepository.save(hello);
        wordEntityRepository.save(ilja);

        WordRelationship relationship = new WordRelationship(hello, ilja);
        wordRelationshipDAO.save(relationship);

        assertNotNull(wordRelationshipDAO.getRelationshipBetween(hello, ilja));
    }

}
