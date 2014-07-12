package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.Assert.*;

public class WordTupleFinderServiceIT extends IntegrationTestsBase {

    @Test
    @Rollback
    public void testGetNextWords() throws Exception {
        textSaverService.saveToRepo("This is text. This is cat. This string should be easy. This is.");

        WordEntity text = wordRepositoryFixedIndexesSearch.findByWord("text");
        WordEntity cat = wordRepositoryFixedIndexesSearch.findByWord("cat");

        List<WordEntity> nextWords = wordTupleFinderService.getNextWords("This", "is");

        assertTrue(nextWords.contains(text));
        assertTrue(nextWords.contains(cat));

        assertEquals(2, nextWords.size());
    }

    @Test
    @Rollback
    public void testGetNextWords3Words() throws Exception {
        textSaverService.saveToRepo("This is yellow car. This is yellow bus. This is black ledbetter");

        WordEntity car = wordRepositoryFixedIndexesSearch.findByWord("car");
        WordEntity bus = wordRepositoryFixedIndexesSearch.findByWord("bus");

        List<WordEntity> nextWords = wordTupleFinderService.getNextWords("This", "is", "yellow");

        assertTrue(nextWords.contains(car));
        assertTrue(nextWords.contains(bus));

        assertEquals(2, nextWords.size());
    }

}