package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class WordRepositoryFixedIndexesSearchTest {

    @InjectMocks
    private WordRepositoryFixedIndexesSearch wordRepositoryFixedIndexesSearch;

    @Mock
    private WordRepository wordRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindByWord() throws Exception {
        WordEntity small = new WordEntity("ilja");
        small.setId(1l);
        WordEntity big = new WordEntity("Ilja");
        big.setId(2l);
        ArrayList<WordEntity> wordEntitites = new ArrayList<>();
        wordEntitites.add(small);
        wordEntitites.add(big);

        when(wordRepository.findByWord("Ilja")).thenReturn(wordEntitites);
        when(wordRepository.findByWord("ilja")).thenReturn(wordEntitites);

        assertEquals(small, wordRepositoryFixedIndexesSearch.findByWord("ilja"));
        assertEquals(big, wordRepositoryFixedIndexesSearch.findByWord("Ilja"));
    }

    @Test
    public void testFindByWordException() throws Exception {
        when(wordRepository.findByWord(anyString())).thenThrow(new NullPointerException());
        assertEquals(null, wordRepositoryFixedIndexesSearch.findByWord("ilja"));
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

        assertTrue(wordRepositoryFixedIndexesSearch.findByWordStartingWith("il").contains(small));
        assertTrue(wordRepositoryFixedIndexesSearch.findByWordStartingWith("il").contains(medium));
        assertFalse(wordRepositoryFixedIndexesSearch.findByWordStartingWith("il").contains(big));
    }

    @Test
    public void testFindByWordStartingWithThrowException() throws Exception {
        when(wordRepository.findByWordRegexOrderByPopularity(anyString())).thenThrow(new NullPointerException());
        assertEquals(0, wordRepositoryFixedIndexesSearch.findByWordStartingWith("ilja").size());
    }
}