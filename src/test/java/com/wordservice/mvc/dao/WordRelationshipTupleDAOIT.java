package com.wordservice.mvc.dao;

import com.google.common.collect.Lists;
import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationshipTuple;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class WordRelationshipTupleDAOIT extends IntegrationTestsBase {

    WordEntity hello = new WordEntity("Hello");
    WordEntity my = new WordEntity("my");
    WordEntity sad = new WordEntity("sad");
    WordEntity grey = new WordEntity("grey");
    WordEntity world = new WordEntity("world");
    WordEntity elephant = new WordEntity("elephant");

    @Before
    public void init() {
        wordRepository.save(hello);
        wordRepository.save(grey);
        wordRepository.save(my);
        wordRepository.save(sad);
        wordRepository.save(world);
        wordRepository.save(elephant);
    }

    @Test
    @Rollback
    public void getRelationshipBetween() throws Exception {
        wordRelationshipTupleDAO.save(new WordRelationshipTuple(hello, my, sad, world));

        assertNotNull(wordRelationshipTupleDAO.getRelationshipBetween(hello, my));
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsList() {
        wordRelationshipTupleDAO.save(new WordRelationshipTuple(hello, my, sad, world));
        wordRelationshipTupleDAO.save(new WordRelationshipTuple(hello, my, grey, world));


        List<WordRelationshipTuple> relationshipsBetweenAsList = wordRelationshipTupleDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(2, relationshipsBetweenAsList.size());
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsIterable() {
        wordRelationshipTupleDAO.save(new WordRelationshipTuple(hello, my, sad, world));

        Iterable<WordRelationshipTuple> relationshipsBetweenAsList = wordRelationshipTupleDAO.getRelationshipsBetweenAsIterable(hello, my);
        Iterator<WordRelationshipTuple> iterator = relationshipsBetweenAsList.iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
        assertFalse(wordRelationshipTupleDAO.getRelationshipsBetweenAsIterable(hello, world).iterator().hasNext());
    }

    @Test
    @Rollback
    public void createOrIncrementPopularityOfWordRelationshipTuple() {
        wordRelationshipTupleDAO.createOrIncrementPopularityOfWordRelationshipTuple(hello, my, sad, world);
        wordRelationshipTupleDAO.createOrIncrementPopularityOfWordRelationshipTuple(hello, my, sad, world);

        List<WordRelationshipTuple> relationshipsBetweenAsList = wordRelationshipTupleDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(1, relationshipsBetweenAsList.size());
        assertEquals(2, relationshipsBetweenAsList.get(0).getPopularity());
    }

    @Test
    @Rollback
    public void saveWordRelationshipTuples() {
        wordRelationshipTupleDAO.saveWordRelationshipTuples(Arrays.asList(hello, my, sad, grey, world));

        List<WordRelationshipTuple> helloMy = wordRelationshipTupleDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(1, helloMy.size());
        assertEquals(sad.getId(), helloMy.get(0).getThird());
        assertEquals(grey.getId(), helloMy.get(0).getFourth());

        List<WordRelationshipTuple> mySad = wordRelationshipTupleDAO.getRelationshipsBetweenAsList(my, sad);
        assertEquals(1, mySad.size());
        assertEquals(grey.getId(), mySad.get(0).getThird());
        assertEquals(world.getId(), mySad.get(0).getFourth());

        List<WordRelationshipTuple> sadGrey = wordRelationshipTupleDAO.getRelationshipsBetweenAsList(sad, grey);
        assertEquals(1, sadGrey.size());
        assertEquals(world.getId(), sadGrey.get(0).getThird());
        assertEquals(0l, sadGrey.get(0).getFourth());

        List<WordRelationshipTuple> greyWorld = wordRelationshipTupleDAO.getRelationshipsBetweenAsList(grey, world);
        assertEquals(1, greyWorld.size());
        assertEquals(0l, greyWorld.get(0).getThird());
        assertEquals(0l, greyWorld.get(0).getFourth());
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsList3args() {
        wordRelationshipTupleDAO.save(new WordRelationshipTuple(hello, my, sad, world));
        wordRelationshipTupleDAO.save(new WordRelationshipTuple(hello, my, sad, elephant));
        List<WordRelationshipTuple> relationshipsBetweenAsList
                = wordRelationshipTupleDAO.getRelationshipsBetweenAsList(hello, my, sad);
        assertEquals(2,relationshipsBetweenAsList.size());
    }

}
