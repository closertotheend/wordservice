package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.model.WordRelationshipTuple;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static org.junit.Assert.assertNotNull;

public class WordRelationshipTupleRepositoryIT extends IntegrationTestsBase {
    public void testSave() throws Exception {

    }

    @Test
    @Rollback
    public void testGetRelationshipBetween() throws Exception {
        WordEntity hello = new WordEntity("Hello");
        WordEntity my = new WordEntity("my");
        WordEntity sad = new WordEntity("sad");
        WordEntity world = new WordEntity("world");

        wordRepository.save(hello);
        wordRepository.save(my);
        wordRepository.save(sad);
        wordRepository.save(world);

        WordRelationshipTuple relationship = new WordRelationshipTuple(hello,my,sad,world);
        wordRelationshipTupleRepository.save(relationship);

        assertNotNull(wordRelationshipTupleRepository.getRelationshipBetween(sad, world));
    }

}
