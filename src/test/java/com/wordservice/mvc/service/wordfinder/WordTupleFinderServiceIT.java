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
    public void testGetNextWords() throws Exception {
        textSaverService.saveToRepo("This is text. This is cat. This string should be easy. This is.");

        WordEntity text = wordEntityDAO.findByWord("text");
        WordEntity cat = wordEntityDAO.findByWord("cat");

        List<WordEntity> nextWords = wordTupleFinderService.getNextWords("This", "is");

        assertTrue(nextWords.contains(text));
        assertTrue(nextWords.contains(cat));

        assertEquals(2, nextWords.size());
    }

    @Test
    @Rollback
    public void testGetNextWords3Words() throws Exception {
        textSaverService.saveToRepo("This is yellow car. This is yellow bus. This is black ledbetter");

        WordEntity car = wordEntityDAO.findByWord("car");
        WordEntity bus = wordEntityDAO.findByWord("bus");

        List<WordEntity> nextWords = wordTupleFinderService.getNextWords("This", "is", "yellow");

        assertTrue(nextWords.contains(car));
        assertTrue(nextWords.contains(bus));

        assertEquals(2, nextWords.size());
    }

}