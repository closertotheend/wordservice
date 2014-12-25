package com.wordservice.mvc.dao;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
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
        wordEntityRepository.save(hello);
        wordEntityRepository.save(grey);
        wordEntityRepository.save(my);
        wordEntityRepository.save(sad);
        wordEntityRepository.save(world);
        wordEntityRepository.save(elephant);
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsList() {
        wordRelationshipDAO.save(new WordRelationship(hello, my, sad, world));
        wordRelationshipDAO.save(new WordRelationship(hello, my, grey, world));


        List<WordRelationship> relationshipsBetweenAsList = wordRelationshipDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(2, relationshipsBetweenAsList.size());
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsListNoResults() {
        wordRelationshipDAO.save(new WordRelationship(hello, my, sad, world));

        Iterable<WordRelationship> relationshipsBetweenAsList = wordRelationshipDAO.getRelationshipsBetweenAsList(hello, my);
        Iterator<WordRelationship> iterator = relationshipsBetweenAsList.iterator();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
        assertFalse(wordRelationshipDAO.getRelationshipsBetweenAsList(hello, world).iterator().hasNext());
    }

    @Test
    @Rollback
    public void createOrIncrementPopularityOfWordRelationshipTuple() {
        wordRelationshipDAO.createOrIncrementPopularityOfWordRelationshipTuple(hello, my, sad, world);
        wordRelationshipDAO.createOrIncrementPopularityOfWordRelationshipTuple(hello, my, sad, world);

        List<WordRelationship> relationshipsBetweenAsList = wordRelationshipDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(1, relationshipsBetweenAsList.size());
        assertEquals(2, relationshipsBetweenAsList.get(0).getPopularity());
    }

    @Test
    @Rollback
    public void saveWordRelationshipTuples() {
        wordRelationshipDAO.saveWordRelationshipTuples(Arrays.asList(hello, my, sad, grey, world));

        List<WordRelationship> helloMy = wordRelationshipDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(1, helloMy.size());
        assertEquals(sad.getId(), helloMy.get(0).getThird());
        assertEquals(grey.getId(), helloMy.get(0).getFourth());

        List<WordRelationship> mySad = wordRelationshipDAO.getRelationshipsBetweenAsList(my, sad);
        assertEquals(1, mySad.size());
        assertEquals(grey.getId(), mySad.get(0).getThird());
        assertEquals(world.getId(), mySad.get(0).getFourth());

        List<WordRelationship> sadGrey = wordRelationshipDAO.getRelationshipsBetweenAsList(sad, grey);
        assertEquals(1, sadGrey.size());
        assertEquals(world.getId(), sadGrey.get(0).getThird());
        assertEquals(0l, sadGrey.get(0).getFourth());

        List<WordRelationship> greyWorld = wordRelationshipDAO.getRelationshipsBetweenAsList(grey, world);
        assertEquals(1, greyWorld.size());
        assertEquals(0l, greyWorld.get(0).getThird());
        assertEquals(0l, greyWorld.get(0).getFourth());
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsList3args() {
        wordRelationshipDAO.save(new WordRelationship(hello, my, sad, world));
        wordRelationshipDAO.save(new WordRelationship(hello, my, sad, elephant));
        List<WordRelationship> relationshipsBetweenAsList
                = wordRelationshipDAO.getRelationshipsBetweenAsList(hello, my, sad);
        assertEquals(2,relationshipsBetweenAsList.size());
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsList4args() {
        WordRelationship helloMySadWorld = new WordRelationship(hello, my, sad, world);
        wordRelationshipDAO.save(helloMySadWorld);
        wordRelationshipDAO.save(new WordRelationship(hello, my, sad, elephant));
        assertEquals(helloMySadWorld, wordRelationshipDAO.getRelationshipBetween(hello, my, sad, world));
    }

}
