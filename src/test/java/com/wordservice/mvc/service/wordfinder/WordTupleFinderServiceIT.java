package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationshipTuple;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WordTupleFinderServiceIT extends IntegrationTestsBase {


    @Test
    @Rollback
    public void getNextWordsViaTuple3args() throws Exception {
        textSaverService.saveToRepo("This is yellow car. This is yellow bus. This is black ledbetter");

        WordEntity car = wordEntityDAO.findByWord("car");
        WordEntity bus = wordEntityDAO.findByWord("bus");

        List<WordEntity> nextWords = wordTupleFinderService.getNextWordsViaTuple("This", "is", "yellow");

        assertTrue(nextWords.contains(car));
        assertTrue(nextWords.contains(bus));

        assertEquals(2, nextWords.size());
    }

    @Test
    @Rollback
    public void getNextWordsViaTuple() {
        textSaverService.saveToRepo("This is grey car. This is yellow bus. This is black ledbetter");

        List<WordEntity> nextWords = wordTupleFinderService.getNextWordsViaTuple("This", "is");

        WordEntity grey = wordEntityDAO.findByWord("grey");
        WordEntity yellow = wordEntityDAO.findByWord("yellow");
        WordEntity black = wordEntityDAO.findByWord("black");

        assertEquals(3, nextWords.size());

        assertTrue(nextWords.contains(grey));
        assertTrue(nextWords.contains(yellow));
        assertTrue(nextWords.contains(black));

    }


}