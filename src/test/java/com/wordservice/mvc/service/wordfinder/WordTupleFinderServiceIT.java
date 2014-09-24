package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WordTupleFinderServiceIT extends IntegrationTestsBase {


    @Test
    @Rollback
    public void getNextWordsViaTuple3args() throws Exception {
        textSaverService.saveToRepo("This is yellow car. This is yellow bus. This is black ledbetter");

        WordEntity car = wordEntityDAO.findByWordViaIndexAndRegex("car");
        WordEntity bus = wordEntityDAO.findByWordViaIndexAndRegex("bus");

        List<WordEntity> nextWords = wordFinderService.getNextWordsViaTuple("This", "is", "yellow");

        assertTrue(nextWords.contains(car));
        assertTrue(nextWords.contains(bus));

        assertEquals(2, nextWords.size());
    }

    @Test
    @Rollback
    public void getNextWordsViaTuple() {
        textSaverService.saveToRepo("This is grey car. This is yellow bus. This is black ledbetter");

        List<WordEntity> nextWords = wordFinderService.getNextWordsViaTuple("This", "is");

        WordEntity grey = wordEntityDAO.findByWordViaIndexAndRegex("grey");
        WordEntity yellow = wordEntityDAO.findByWordViaIndexAndRegex("yellow");
        WordEntity black = wordEntityDAO.findByWordViaIndexAndRegex("black");

        assertEquals(3, nextWords.size());

        assertTrue(nextWords.contains(grey));
        assertTrue(nextWords.contains(yellow));
        assertTrue(nextWords.contains(black));

    }


}