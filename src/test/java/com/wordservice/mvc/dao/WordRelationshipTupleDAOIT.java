package com.wordservice.mvc.dao;

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
        wordEntityRepository.save(hello);
        wordEntityRepository.save(grey);
        wordEntityRepository.save(my);
        wordEntityRepository.save(sad);
        wordEntityRepository.save(world);
        wordEntityRepository.save(elephant);
    }

    @Test
    @Rollback
    public void getRelationshipBetween() throws Exception {
        wordRelationshipDAO.save(new WordRelationshipTuple(hello, my, sad, world));

        assertNotNull(wordRelationshipDAO.getRelationshipBetween(hello, my));
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsList() {
        wordRelationshipDAO.save(new WordRelationshipTuple(hello, my, sad, world));
        wordRelationshipDAO.save(new WordRelationshipTuple(hello, my, grey, world));


        List<WordRelationshipTuple> relationshipsBetweenAsList = wordRelationshipDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(2, relationshipsBetweenAsList.size());
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsListNoResults() {
        wordRelationshipDAO.save(new WordRelationshipTuple(hello, my, sad, world));

        Iterable<WordRelationshipTuple> relationshipsBetweenAsList = wordRelationshipDAO.getRelationshipsBetweenAsList(hello, my);
        Iterator<WordRelationshipTuple> iterator = relationshipsBetweenAsList.iterator();
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

        List<WordRelationshipTuple> relationshipsBetweenAsList = wordRelationshipDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(1, relationshipsBetweenAsList.size());
        assertEquals(2, relationshipsBetweenAsList.get(0).getPopularity());
    }

    @Test
    @Rollback
    public void saveWordRelationshipTuples() {
        wordRelationshipDAO.saveWordRelationshipTuples(Arrays.asList(hello, my, sad, grey, world));

        List<WordRelationshipTuple> helloMy = wordRelationshipDAO.getRelationshipsBetweenAsList(hello, my);
        assertEquals(1, helloMy.size());
        assertEquals(sad.getId(), helloMy.get(0).getThird());
        assertEquals(grey.getId(), helloMy.get(0).getFourth());

        List<WordRelationshipTuple> mySad = wordRelationshipDAO.getRelationshipsBetweenAsList(my, sad);
        assertEquals(1, mySad.size());
        assertEquals(grey.getId(), mySad.get(0).getThird());
        assertEquals(world.getId(), mySad.get(0).getFourth());

        List<WordRelationshipTuple> sadGrey = wordRelationshipDAO.getRelationshipsBetweenAsList(sad, grey);
        assertEquals(1, sadGrey.size());
        assertEquals(world.getId(), sadGrey.get(0).getThird());
        assertEquals(0l, sadGrey.get(0).getFourth());

        List<WordRelationshipTuple> greyWorld = wordRelationshipDAO.getRelationshipsBetweenAsList(grey, world);
        assertEquals(1, greyWorld.size());
        assertEquals(0l, greyWorld.get(0).getThird());
        assertEquals(0l, greyWorld.get(0).getFourth());
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsList3args() {
        wordRelationshipDAO.save(new WordRelationshipTuple(hello, my, sad, world));
        wordRelationshipDAO.save(new WordRelationshipTuple(hello, my, sad, elephant));
        List<WordRelationshipTuple> relationshipsBetweenAsList
                = wordRelationshipDAO.getRelationshipsBetweenAsList(hello, my, sad);
        assertEquals(2,relationshipsBetweenAsList.size());
    }

    @Test
    @Rollback
    public void getRelationshipsBetweenAsList4args() {
        WordRelationshipTuple helloMySadWorld = new WordRelationshipTuple(hello, my, sad, world);
        wordRelationshipDAO.save(helloMySadWorld);
        wordRelationshipDAO.save(new WordRelationshipTuple(hello, my, sad, elephant));
        assertEquals(helloMySadWorld, wordRelationshipDAO.getRelationshipBetween(hello, my, sad, world));
    }

}
