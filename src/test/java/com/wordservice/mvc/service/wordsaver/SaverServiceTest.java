package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordEntityRepository;
import com.wordservice.mvc.dao.WordEntityDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SaverServiceTest {
    @Mock
    WordEntityRepository wordEntityRepository;

    @Mock
    WordEntityDAO wordEntityDAO;

    @InjectMocks
    SaverService saverService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetOrCreateWordEntity() throws Exception {
        WordEntity existingWordEntity = new WordEntity();
        when(wordEntityDAO.findByWord(anyString())).thenReturn(existingWordEntity);
        saverService.getOrCreateWordEntity(anyString());
        verify(wordEntityRepository).save(existingWordEntity);
        assertEquals("saving twice enforces incrementation of popularity",1,existingWordEntity.getPopularity());
    }

}