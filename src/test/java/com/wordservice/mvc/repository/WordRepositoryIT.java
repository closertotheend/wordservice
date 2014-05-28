package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import static junit.framework.Assert.*;

public class WordRepositoryIT extends IntegrationTestsBase {


    @Test
    @Rollback
    public void testFindByWord() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja!");
        assertNotNull(wordRepository.findByWord("Hello").size()>0);
    }

    @Test
    @Rollback
    public void testGetTop10Words() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja, I am neo4j, I am slow, I am slow, I am slow and ugly! I am happy, I am happy.");
        assertEquals("There is 3 words followed after word am" , 3 , wordRepository.getTop10WordsAfter("am").size());
    }

    @Test
    @Rollback
    public void testFindByWordContaining() throws Exception {
        wordEntitySaverService.saveToRepo("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also.");
        assertEquals( 1 , wordRepository.findByWordContaining("Pneumono").size());
        assertEquals( 1 , wordRepository.findByWordContaining("croscopicsi").size());
        assertEquals("firstone  + is", 2 , wordRepository.findByWordContaining("is").size());
    }

    @Test
    @Rollback
    public void testFindByWordStartingWith() throws Exception {
        wordEntitySaverService.saveToRepo("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also.");
        assertEquals( 1 , wordRepository.findByWordStartingWith("Pneumono").size());
        assertEquals( 0 , wordRepository.findByWordStartingWith("croscopicsi").size());
        assertEquals("is", 1 , wordRepository.findByWordStartingWith("i").size());
    }

    @Test
    @Rollback
    public void testGetTop10WordsWith2Arguments() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja, I am neo4j database, I am slow, I am slow, I am slow and ugly! I am happy, I am happy. " +
                "This am does nothing but still is in context");
        assertEquals("There is 4 words followed after words I&am" , 4 , wordRepository.getTop10WordsAfter("I","am").size());
        assertEquals("There is NO words followed after words I&neo4j" , 0 , wordRepository.getTop10WordsAfter("I","neo4j").size());
    }

    @Test
    @Rollback
    public void testApacheLuceneIsWorking() throws Exception {
        wordEntitySaverService.saveToRepo("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also.");
        long startTime = System.currentTimeMillis();
        wordRepository.findByWordContaining("Pneumono");
        wordRepository.findByWordContaining("croscopicsi");
        wordRepository.findByWordContaining("is");
        long estimatedTime = System.currentTimeMillis() - startTime;
        assertTrue(estimatedTime<30);
    }

    @Test
    @Rollback
    public void testApacheLuceneIsWorking2() throws Exception {
        wordEntitySaverService.saveToRepo("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also.");
        long startTime = System.currentTimeMillis();
        wordRepository.findByWordStartingWith("Pneumono");
        wordRepository.findByWordStartingWith("croscopicsi");
        wordRepository.findByWordStartingWith("i");
        long estimatedTime = System.currentTimeMillis() - startTime;
        assertTrue(estimatedTime<30);
    }

}
