package com.wordservice.mvc.repository;

import com.wordservice.mvc.IntegrationTestsBase;
import com.wordservice.mvc.model.WordEntity;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import java.util.Iterator;
import java.util.List;

import static junit.framework.Assert.*;

public class WordEntityRepositoryIT extends IntegrationTestsBase {


    @Test
    @Rollback
    public void testFindByWord() throws Exception {
        textSaverService.save("Hello Ilja!");
        assertNotNull(wordEntityRepository.findByWord("Hello").size()>0);
    }

    @Test
    @Rollback
    public void testGetTop10Words() throws Exception {
        textSaverService.save("Hello Ilja, I am neo4j, I am slow, I am slow, I am slow and ugly! I am happy, I am happy.");
        assertEquals("There is 3 words followed after word am (but 2 of them also contain comma)" , 5 , wordEntityRepository.getTop10WordsAfter("am").size());
    }

    @Test
    @Rollback
    public void testFindByWordContaining() throws Exception {
        textSaverService.save("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also.");
        assertEquals(1, wordEntityRepository.findByWordRegexOrderByPopularity(".*Pneumono.*").size());
        assertEquals( 1 , wordEntityRepository.findByWordRegexOrderByPopularity(".*croscopicsi.*").size());
        assertEquals("firstone  + is", 2, wordEntityRepository.findByWordRegexOrderByPopularity(".*is.*").size());
    }

    @Test
    @Rollback
    public void findByWordContainingOrderByPopularityDesc() throws Exception {
        textSaverService.save("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also. " +
                "The is is also popular word, but his his is not. Wis lis dis gis wais mis zis this iss isss sois");
        assertEquals("Maximum capacity is 20", 14, wordEntityRepository.findByWordRegexOrderByPopularity(".*is.*").size());
    }

    @Test
    @Rollback
    public void testFindByWordStartingWith() throws Exception {
        textSaverService.save("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also.");
        assertEquals( 1 , wordEntityRepository.findByWordRegexOrderByPopularity("Pneumono.*").size());
        assertEquals( 0 , wordEntityRepository.findByWordRegexOrderByPopularity("croscopicsi.*").size());
        assertEquals("is", 1, wordEntityRepository.findByWordRegexOrderByPopularity("i.*").size());
    }

    @Test
    @Rollback
    public void findByWordStartingWithOrderByPopularityDesc() throws Exception {
        textSaverService.save("iu in in ia i is is is is is is is is is i i i i i ia ia ia ia");
        List<WordEntity> i = wordEntityRepository.findByWordRegexOrderByPopularity("i.*");
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
        textSaverService.save("Hello Ilja, I am neo4j database, I am slow, I am slow, I am slow and ugly! I am happy, I am happy. " +
                "This am does nothing but still is in context");
        assertEquals("There is 4 words followed after words I&am but 2 of them are with comma" , 6 , wordEntityRepository.getTop10WordsAfter("I","am").size());
        assertEquals("There is NO words followed after words I&neo4j" , 0 , wordEntityRepository.getTop10WordsAfter("I","neo4j").size());
    }

    @Test
    @Rollback
    @Ignore
    public void testApacheLuceneIsWorking() throws Exception {
        textSaverService.save("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also.");
        long startTime = System.currentTimeMillis();
        wordEntityRepository.findByWordRegexOrderByPopularity(".*Pneumono.*");
        wordEntityRepository.findByWordRegexOrderByPopularity(".*croscopicsi.*");
        wordEntityRepository.findByWordRegexOrderByPopularity(".*is.*");
        long estimatedTime = System.currentTimeMillis() - startTime;
        assertTrue("Time is " + estimatedTime,estimatedTime<30);
    }

    @Test
    @Rollback
    @Ignore
    public void testApacheLuceneIsWorking2() throws Exception {
        textSaverService.save("Pneumonoultramicroscopicsilicovolcanoconiosis is huge word. Honorificabilitudinitatibus is also.");
        long startTime = System.currentTimeMillis();
        wordEntityRepository.findByWordRegexOrderByPopularity("Pneumono");
        wordEntityRepository.findByWordRegexOrderByPopularity("croscopicsi");
        wordEntityRepository.findByWordRegexOrderByPopularity("i");
        long estimatedTime = System.currentTimeMillis() - startTime;
        assertTrue("Time is " + estimatedTime, estimatedTime < 30);
    }

    @Test
    @Rollback
    public void testFindByWordIgnoreCase() throws Exception {
        textSaverService.save("Hello Ilja! hello martin");
        List<WordEntity> bigLetterHello = wordEntityRepository.findByWord("Hello");
        assertEquals(2, bigLetterHello.size());
        List<WordEntity> smallLetterHello = wordEntityRepository.findByWord("hello");
        assertEquals(2, smallLetterHello.size());
    }

    @Test
    @Rollback
    public void  findByWordWithoutFastIndex() throws Exception {
        textSaverService.save("Hello Ilja! hello martin");
        WordEntity bigLetterHello = wordEntityRepository.findByWordWithoutFastIndex("Hello");
        WordEntity smallLetterHello = wordEntityRepository.findByWordWithoutFastIndex("hello");
        assertNotNull(bigLetterHello);
        assertNotNull(smallLetterHello);
        assertFalse(bigLetterHello.equals(smallLetterHello));
    }

    @Test
    @Rollback
    public void  findByWordContaining() throws Exception {
        textSaverService.save("Hello Ilja! Hello martin! hello hell! hell hell");
        Iterable<WordEntity> hellIterable = wordEntityRepository.findByWordContainingOrderByPopularityDesc("hell");
        Iterator<WordEntity> hell = hellIterable.iterator();
        assertEquals(hell.next().getWord(), "hell");
        assertEquals(hell.next().getWord() , "Hello");
    }

    @Test
    @Rollback
    public void  findByWordStartingWithOrderByPopularityDescFastIndex() throws Exception {
        textSaverService.save("Hello Ilja! Hello martin! Ohello");
        Iterable<WordEntity> hellIterable = wordEntityRepository.findByWordStartingWithOrderByPopularityDesc("hell");
        Iterator<WordEntity> hell = hellIterable.iterator();
        assertEquals(hell.next().getWord(), "Hello");
        assertFalse(hell.hasNext());
    }

}
