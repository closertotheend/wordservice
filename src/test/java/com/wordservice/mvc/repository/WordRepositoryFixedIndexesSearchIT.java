package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;

public class WordRepositoryFixedIndexesSearchIT extends IntegrationTestsBase{

    @Test
    @Rollback
    public void testFindByWordCheckCase() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja! hello martin");
        WordEntity bigLetterHello = wordRepositoryFixedIndexesSearch.findByWord("Hello");
        assertNotNull(bigLetterHello);
        WordEntity smallLetterHello = wordRepositoryFixedIndexesSearch.findByWord("hello");
        assertNotNull(smallLetterHello);
        assertFalse(bigLetterHello.equals(smallLetterHello));
        assertEquals("Hello",bigLetterHello.getWord());
        assertEquals("hello",smallLetterHello.getWord());
    }

    @Test
    @Rollback
    public void testFindByWordStartingWith() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja! hello martin");
        List<WordEntity> bigLetterHello = wordRepositoryFixedIndexesSearch.findByWordStartingWith("Hello");
        assertEquals(1,bigLetterHello.size());
    }

}