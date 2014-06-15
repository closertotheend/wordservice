package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.List;

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
        assertEquals(1, wordRepository.findByWordRegexOrderByPopularity(".*Pneumono.*").size());
        assertEquals( 1 , wordRepository.findByWordRegexOrderByPopularity(".*croscopicsi.*").size());
        assertEquals("firstone  + is", 2, wordRepository.findByWordRegexOrderByPopularity(".*is.*").size());
    }

    @Test
    @Rollback
    public void findByWordContainingOrderByPopularityDesc() throws Exception {
        wordEntitySaverService.saveToRepo("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also. " +
                "The is is also popular word, but his his is not. Wis lis dis gis wais mis zis this iss isss sois");
        assertEquals("Maximum capacity is 10", 10, wordRepository.findByWordRegexOrderByPopularity(".*is.*").size());
    }

    @Test
    @Rollback
    public void testFindByWordStartingWith() throws Exception {
        wordEntitySaverService.saveToRepo("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also.");
        assertEquals( 1 , wordRepository.findByWordRegexOrderByPopularity("Pneumono.*").size());
        assertEquals( 0 , wordRepository.findByWordRegexOrderByPopularity("croscopicsi.*").size());
        assertEquals("is", 1, wordRepository.findByWordRegexOrderByPopularity("i.*").size());
    }

    @Test
    @Rollback
    public void findByWordStartingWithOrderByPopularityDesc() throws Exception {
        wordEntitySaverService.saveToRepo("iu in in ia i is is is is is is is is is i i i i i ia ia ia ia");
        List<WordEntity> i = wordRepository.findByWordRegexOrderByPopularity("i.*");
        assertEquals(5, i.size());
        assertEquals("is", i.get(0).getWord());
        assertEquals("i", i.get(1).getWord());
        assertEquals("ia", i.get(2).getWord());
        assertEquals("in", i.get(3).getWord());
        assertEquals("iu", i.get(4).getWord());
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
        wordRepository.findByWordRegexOrderByPopularity(".*Pneumono.*");
        wordRepository.findByWordRegexOrderByPopularity(".*croscopicsi.*");
        wordRepository.findByWordRegexOrderByPopularity(".*is.*");
        long estimatedTime = System.currentTimeMillis() - startTime;
        assertTrue("Time is " + estimatedTime,estimatedTime<30);
    }

    @Test
    @Rollback
    public void testApacheLuceneIsWorking2() throws Exception {
        wordEntitySaverService.saveToRepo("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also.");
        long startTime = System.currentTimeMillis();
        wordRepository.findByWordRegexOrderByPopularity("Pneumono");
        wordRepository.findByWordRegexOrderByPopularity("croscopicsi");
        wordRepository.findByWordRegexOrderByPopularity("i");
        long estimatedTime = System.currentTimeMillis() - startTime;
        assertTrue("Time is " + estimatedTime, estimatedTime < 30);
    }

    @Test
    @Rollback
    public void testFindByWordIgnoreCase() throws Exception {
        wordEntitySaverService.saveToRepo("Hello Ilja! hello martin");
        List<WordEntity> bigLetterHello = wordRepository.findByWord("Hello");
        assertEquals(2, bigLetterHello.size());
        List<WordEntity> smallLetterHello = wordRepository.findByWord("hello");
        assertEquals(2, smallLetterHello.size());
    }

}
