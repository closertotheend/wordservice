package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.dao.WordRelationshipDAO;
import com.wordservice.mvc.repository.WordEntityRepository;
import com.wordservice.mvc.dao.WordEntityDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;


public class TextSaverServiceTest {

    @Mock
    WordEntityRepository wordEntityRepository;

    @Mock
    WordEntityDAO wordEntityDAO;

    @Mock
    WordRelationshipDAO WordRelationshipDAO;

    @InjectMocks
    TextSaverService textSaverService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveToRepo() throws Exception {
        textSaverService.save("Hello World!");
    }

    @Test
    public void testSaveToRepo2() throws Exception {
        textSaverService.save("Hello World! Hello Sun and Earth.");
        int numberOfWords = 6;
        int numberOfRelationsBetween2Words = 4;
    }


}
