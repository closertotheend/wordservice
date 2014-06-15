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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class WordEntitySaverServiceTest {

    @Mock
    WordRelationshipRepository wordRelationshipRepository;

    @Mock
    WordRepository wordRepository;

    @Mock
    SentenceRepository sentenceRepository;

    @Mock
    WordRepositoryFixedIndexesSearch wordRepositoryFixedIndexesSearch;

    @InjectMocks
    WordEntitySaverService wordEntitySaverService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveToRepo() throws Exception {
        when(wordEntitySaverService.createOrIncrementPopularityOfRelationship(any(WordEntity.class),any(WordEntity.class)))
                .thenReturn(new WordRelationship());
        wordEntitySaverService.saveToRepo("Hello World!");

        verify(wordRepository, times(2)).save(any(WordEntity.class));
        verify(wordRelationshipRepository).save(any(WordRelationship.class));
        verify(wordRelationshipRepository, atLeastOnce()).getRelationshipBetween(any(WordEntity.class), any(WordEntity.class));
    }

    @Test
    public void testSaveToRepo2() throws Exception {
        when(wordEntitySaverService.createOrIncrementPopularityOfRelationship(any(WordEntity.class),any(WordEntity.class)))
                .thenReturn(new WordRelationship());
        wordEntitySaverService.saveToRepo("Hello World! Hello Sun and Earth.");
        int numberOfWords = 6;
        int numberOfRelationsBetween2Words = 4;

        verify(wordRepository, times(numberOfWords)).save(any(WordEntity.class));
        verify(wordRelationshipRepository, times(numberOfRelationsBetween2Words)).save(any(WordRelationship.class));
        verify(wordRelationshipRepository, atLeastOnce()).getRelationshipBetween(any(WordEntity.class), any(WordEntity.class));
    }

    @Test
    public void testGetOrCreateWordEntity() throws Exception {
        WordEntity existingWordEntity = new WordEntity();
        when(wordRepositoryFixedIndexesSearch.findByWord(anyString())).thenReturn(existingWordEntity);
        wordEntitySaverService.getOrCreateWordEntity(anyString());
        verify(wordRepository).save(existingWordEntity);
        assertEquals("saving twice enforces incrementation of popularity",1,existingWordEntity.getPopularity());
    }

    @Test
    public void testCreateOrIncrementPopularityOfRelationship() throws Exception {
        wordEntitySaverService.createOrIncrementPopularityOfRelationship(new WordEntity(),new WordEntity());
        verify(wordRelationshipRepository).getRelationshipBetween(any(WordEntity.class), any(WordEntity.class));
        verify(wordRelationshipRepository).save(any(WordRelationship.class));
    }

    @Test
    public void testCreateOrIncrementPopularityOfRelationship2() throws Exception {
        WordRelationship wordRelationship = new WordRelationship();
        when(wordRelationshipRepository.getRelationshipBetween(any(WordEntity.class), any(WordEntity.class))).thenReturn(wordRelationship);
        wordEntitySaverService.createOrIncrementPopularityOfRelationship(new WordEntity(), new WordEntity());
        verify(wordRelationshipRepository).getRelationshipBetween(any(WordEntity.class), any(WordEntity.class));
        verify(wordRelationshipRepository).save(any(WordRelationship.class));
        assertEquals("wordrelationship existed so saving twice increases popularity",1,wordRelationship.getPopularity());
    }
}
