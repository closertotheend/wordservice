package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.dao.WordRelationshipTupleDAO;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordEntityRepository;
import com.wordservice.mvc.dao.WordEntityDAO;
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
    WordEntityRepository wordEntityRepository;

    @Mock
    WordEntityDAO wordEntityDAO;

    @Mock
    SaverService saverService;

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
        textSaverService.saveToRepo("Hello World!");

        verify(saverService, times(2)).getOrCreateWordEntity(anyString());
    }

    @Test
    public void testSaveToRepo2() throws Exception {
        textSaverService.saveToRepo("Hello World! Hello Sun and Earth.");
        int numberOfWords = 6;
        int numberOfRelationsBetween2Words = 4;

        verify(saverService, times(numberOfWords)).getOrCreateWordEntity(anyString());
    }


}
