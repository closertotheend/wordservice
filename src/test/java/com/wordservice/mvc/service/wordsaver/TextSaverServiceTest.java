package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.dao.WordRelationshipTupleDAO;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.dao.WordRelationshipDAO;
import com.wordservice.mvc.model.WordRelationshipTuple;
import com.wordservice.mvc.repository.WordRepository;
import com.wordservice.mvc.dao.WordEntityDAO;
import com.wordservice.mvc.repository.WordTupleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class TextSaverServiceTest {

    @Mock
    WordRelationshipDAO wordRelationshipDAO;

    @Mock
    WordRepository wordRepository;

    @Mock
    WordEntityDAO wordEntityDAO;

    @Mock
    SaverService saverService;

    @Mock
    WordTupleRepository wordTupleRepository;

    @Mock
    WordRelationshipTupleDAO WordRelationshipTupleDAO;

    @InjectMocks
    TextSaverService textSaverService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveToRepo() throws Exception {
        when(saverService.createOrIncrementPopularityOfRelationship(any(WordEntity.class),any(WordEntity.class)))
                .thenReturn(new WordRelationship());
        textSaverService.saveToRepo("Hello World!");

        verify(saverService, times(2)).getOrCreateWordEntity(anyString());
        verify(saverService).createOrIncrementPopularityOfRelationship(any(WordEntity.class), any(WordEntity.class));
    }

    @Test
    public void testSaveToRepo2() throws Exception {
        when(saverService.createOrIncrementPopularityOfRelationship(any(WordEntity.class),any(WordEntity.class)))
                .thenReturn(new WordRelationship());
        textSaverService.saveToRepo("Hello World! Hello Sun and Earth.");
        int numberOfWords = 6;
        int numberOfRelationsBetween2Words = 4;

        verify(saverService, times(numberOfWords)).getOrCreateWordEntity(anyString());
        verify(saverService, times(numberOfRelationsBetween2Words)).createOrIncrementPopularityOfRelationship(any(WordEntity.class), any(WordEntity.class));
        verify(saverService, times(numberOfRelationsBetween2Words/2)).createOrIncrementPopularityOfWordTuple(any(WordRelationship.class), any(WordRelationship.class));
    }


}
