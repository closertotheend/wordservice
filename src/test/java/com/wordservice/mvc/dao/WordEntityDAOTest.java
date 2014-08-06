package com.wordservice.mvc.dao;

import com.wordservice.mvc.dao.WordEntityDAO;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class WordEntityDAOTest {

    @InjectMocks
    private WordEntityDAO wordEntityDAO;

    @Mock
    private WordRepository wordRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
//    @Ignore
    public void testFindByWord() throws Exception {
        WordEntity small = new WordEntity("ilja");
        small.setId(1l);
        WordEntity big = new WordEntity("Ilja");
        big.setId(2l);
        ArrayList<WordEntity> wordEntitites = new ArrayList<>();
        wordEntitites.add(small);
        wordEntitites.add(big);

        when(wordRepository.findByWordRegexOrderByPopularity("Ilja")).thenReturn(wordEntitites);
        when(wordRepository.findByWordRegexOrderByPopularity("ilja")).thenReturn(wordEntitites);

        assertEquals(small, wordEntityDAO.findByWord("ilja"));
        assertEquals(big, wordEntityDAO.findByWord("Ilja"));
    }

    @Test
    public void testFindByWordException() throws Exception {
        when(wordRepository.findByWord(anyString())).thenThrow(new NullPointerException());
        assertEquals(null, wordEntityDAO.findByWord("ilja"));
    }

    @Test
    public void testFindByWordStartingWith() throws Exception {
        WordEntity small = new WordEntity("ilja");
        small.setId(1l);
        WordEntity big = new WordEntity("Ilja");
        big.setId(2l);
        WordEntity medium = new WordEntity("ill");
        medium.setId(3l);

        ArrayList<WordEntity> wordEntitites = new ArrayList<>();
        wordEntitites.add(small);
        wordEntitites.add(big);
        wordEntitites.add(medium);

        when(wordRepository.findByWordRegexOrderByPopularity("il.*")).thenReturn(wordEntitites);

        assertTrue(wordEntityDAO.findByWordStartingWith("il").contains(small));
        assertTrue(wordEntityDAO.findByWordStartingWith("il").contains(medium));
        assertFalse(wordEntityDAO.findByWordStartingWith("il").contains(big));
    }

    @Test
    public void testFindByWordStartingWithThrowException() throws Exception {
        when(wordRepository.findByWordRegexOrderByPopularity(anyString())).thenThrow(new NullPointerException());
        assertEquals(0, wordEntityDAO.findByWordStartingWith("ilja").size());
    }
}