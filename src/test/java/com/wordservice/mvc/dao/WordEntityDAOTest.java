package com.wordservice.mvc.dao;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordEntityRepository;
import org.junit.Before;
import org.junit.Ignore;
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
    private WordEntityRepository wordEntityRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Ignore
    public void testFindByWord() throws Exception {
        WordEntity small = new WordEntity("ilja");
        small.setId(1l);
        WordEntity big = new WordEntity("Ilja");
        big.setId(2l);
        ArrayList<WordEntity> wordEntitites = new ArrayList<>();
        wordEntitites.add(small);
        wordEntitites.add(big);

        when(wordEntityRepository.findByWordRegexOrderByPopularity("Ilja")).thenReturn(wordEntitites);
        when(wordEntityRepository.findByWordRegexOrderByPopularity("ilja")).thenReturn(wordEntitites);

        assertEquals(small, wordEntityDAO.findByWordViaIndexAndRegex("ilja"));
        assertEquals(big, wordEntityDAO.findByWordViaIndexAndRegex("Ilja"));
    }

    @Test
    @Ignore
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

        when(wordEntityRepository.findByWordRegexOrderByPopularity("il.*")).thenReturn(wordEntitites);

        assertTrue(wordEntityDAO.findByWordStartingWithViaIndex("il").contains(small));
        assertTrue(wordEntityDAO.findByWordStartingWithViaIndex("il").contains(medium));
        assertFalse(wordEntityDAO.findByWordStartingWithViaIndex("il").contains(big));
    }

    @Test
    public void testFindByWordStartingWithThrowException() throws Exception {
        when(wordEntityRepository.findByWordRegexOrderByPopularity(anyString())).thenThrow(new NullPointerException());
        assertEquals(0, wordEntityDAO.findByWordStartingWithViaIndex("ilja").size());
    }
}