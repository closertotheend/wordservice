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

    @Mock
    SaverService saverService;

    @InjectMocks
    WordEntitySaverService wordEntitySaverService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveToRepo() throws Exception {
        when(saverService.createOrIncrementPopularityOfRelationship(any(WordEntity.class),any(WordEntity.class)))
                .thenReturn(new WordRelationship());
        wordEntitySaverService.saveToRepo("Hello World!");

        verify(saverService, times(2)).getOrCreateWordEntity(anyString());
        verify(saverService).createOrIncrementPopularityOfRelationship(any(WordEntity.class),any(WordEntity.class));
    }

    @Test
    public void testSaveToRepo2() throws Exception {
        when(saverService.createOrIncrementPopularityOfRelationship(any(WordEntity.class),any(WordEntity.class)))
                .thenReturn(new WordRelationship());
        wordEntitySaverService.saveToRepo("Hello World! Hello Sun and Earth.");
        int numberOfWords = 6;
        int numberOfRelationsBetween2Words = 4;

        verify(saverService, times(numberOfWords)).getOrCreateWordEntity(anyString());
        verify(saverService, times(numberOfRelationsBetween2Words)).createOrIncrementPopularityOfRelationship(any(WordEntity.class),any(WordEntity.class));
    }


}
