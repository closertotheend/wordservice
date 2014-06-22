package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.SentenceRepository;
import com.wordservice.mvc.repository.WordRelationshipRepository;
import com.wordservice.mvc.repository.WordRepository;
import com.wordservice.mvc.repository.WordRepositoryFixedIndexesSearch;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SaverServiceTest {
    @Mock
    WordRelationshipRepository wordRelationshipRepository;

    @Mock
    WordRepository wordRepository;

    @Mock
    SentenceRepository sentenceRepository;

    @Mock
    WordRepositoryFixedIndexesSearch wordRepositoryFixedIndexesSearch;

    @InjectMocks
    SaverService saverService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetOrCreateWordEntity() throws Exception {
        WordEntity existingWordEntity = new WordEntity();
        when(wordRepositoryFixedIndexesSearch.findByWord(anyString())).thenReturn(existingWordEntity);
        saverService.getOrCreateWordEntity(anyString());
        verify(wordRepository).save(existingWordEntity);
        assertEquals("saving twice enforces incrementation of popularity",1,existingWordEntity.getPopularity());
    }

    @Test
    public void testCreateOrIncrementPopularityOfRelationship() throws Exception {
        saverService.createOrIncrementPopularityOfRelationship(new WordEntity(),new WordEntity());
        verify(wordRelationshipRepository).getRelationshipBetween(any(WordEntity.class), any(WordEntity.class));
        verify(wordRelationshipRepository).save(any(WordRelationship.class));
    }

    @Test
    public void testCreateOrIncrementPopularityOfRelationship2() throws Exception {
        WordRelationship wordRelationship = new WordRelationship();
        when(wordRelationshipRepository.getRelationshipBetween(any(WordEntity.class), any(WordEntity.class))).thenReturn(wordRelationship);
        saverService.createOrIncrementPopularityOfRelationship(new WordEntity(), new WordEntity());
        verify(wordRelationshipRepository).getRelationshipBetween(any(WordEntity.class), any(WordEntity.class));
        verify(wordRelationshipRepository).save(any(WordRelationship.class));
        assertEquals("wordrelationship existed so saving twice increases popularity",1,wordRelationship.getPopularity());
    }
}