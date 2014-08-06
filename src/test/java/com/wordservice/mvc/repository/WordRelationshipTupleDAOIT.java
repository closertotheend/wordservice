package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationshipTuple;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class WordRelationshipTupleDAOIT extends IntegrationTestsBase {

    WordEntity hello = new WordEntity("Hello");
    WordEntity my = new WordEntity("my");
    WordEntity sad = new WordEntity("sad");
    WordEntity grey = new WordEntity("grey");
    WordEntity world = new WordEntity("world");

    @Test
    @Rollback
    public void getRelationshipBetween() throws Exception {
        wordRepository.save(hello);
        wordRepository.save(my);
        wordRepository.save(sad);
        wordRepository.save(world);

        WordRelationshipTuple relationship = new WordRelationshipTuple(hello,my,sad,world);
        wordRelationshipTupleDAO.save(relationship);

        assertNotNull(wordRelationshipTupleDAO.getRelationshipBetween(hello, my));
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsList() {
        wordRepository.save(hello);
        wordRepository.save(grey);
        wordRepository.save(my);
        wordRepository.save(sad);
        wordRepository.save(world);

        WordRelationshipTuple relationship1 = new WordRelationshipTuple(hello,my,sad,world);
        WordRelationshipTuple relationship2 = new WordRelationshipTuple(hello,my,grey,world);
        wordRelationshipTupleDAO.save(relationship1);
        wordRelationshipTupleDAO.save(relationship2);


        List<WordRelationshipTuple> relationshipsBetweenAsList = wordRelationshipTupleDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(2, relationshipsBetweenAsList.size());
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsIterable() {
        wordRepository.save(hello);
        wordRepository.save(grey);
        wordRepository.save(my);
        wordRepository.save(sad);
        wordRepository.save(world);

        WordRelationshipTuple relationship1 = new WordRelationshipTuple(hello,my,sad,world);
        wordRelationshipTupleDAO.save(relationship1);

        Iterable<WordRelationshipTuple> relationshipsBetweenAsList = wordRelationshipTupleDAO.getRelationshipsBetweenAsIterable(hello, my);
        Iterator<WordRelationshipTuple> iterator = relationshipsBetweenAsList.iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
        assertFalse(wordRelationshipTupleDAO.getRelationshipsBetweenAsIterable(hello, world).iterator().hasNext());
    }

    @Test
    @Rollback
    public void createOrIncrementPopularityOfWordRelationshipTuple(){
        wordRepository.save(hello);
        wordRepository.save(my);
        wordRepository.save(sad);
        wordRepository.save(world);

        wordRelationshipTupleDAO.createOrIncrementPopularityOfWordRelationshipTuple(hello, my, sad, world);
        wordRelationshipTupleDAO.createOrIncrementPopularityOfWordRelationshipTuple(hello, my, sad, world);

        List<WordRelationshipTuple> relationshipsBetweenAsList = wordRelationshipTupleDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(1, relationshipsBetweenAsList.size());
        assertEquals(2, relationshipsBetweenAsList.get(0).getPopularity());
    }

}
