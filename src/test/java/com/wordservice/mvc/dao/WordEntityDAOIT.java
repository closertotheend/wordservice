package com.wordservice.mvc.dao;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static junit.framework.Assert.*;

public class WordEntityDAOIT extends IntegrationTestsBase{

    @Test
    @Rollback
    public void testFindByWordCheckCase() throws Exception {
        textSaverService.saveToRepo("Hello Ilja! hello martin");
        WordEntity bigLetterHello = wordEntityDAO.findByWord("Hello");
        assertNotNull(bigLetterHello);
        WordEntity smallLetterHello = wordEntityDAO.findByWord("hello");
        assertNotNull(smallLetterHello);
        assertFalse(bigLetterHello.equals(smallLetterHello));
        assertEquals("Hello",bigLetterHello.getWord());
        assertEquals("hello",smallLetterHello.getWord());
    }

    @Test
    @Rollback
    public void testFindByWordStartingWith() throws Exception {
        textSaverService.saveToRepo("Hello Ilja! hello martin");
        List<WordEntity> bigLetterHello = wordEntityDAO.findByWordStartingWith("Hello");
        assertEquals(1,bigLetterHello.size());
    }

}