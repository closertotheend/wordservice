package com.wordservice.mvc.dao;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static junit.framework.Assert.*;

public class WordEntityDAOIT extends IntegrationTestsBase{

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

    @Test
    @Rollback
    public void findById() throws Exception {
        assertEquals(hello ,wordEntityDAO.findById(hello.getId()));
    }

}