package com.wordservice.mvc.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class WordEntityTest {

    @Test
    public void testEquals() throws Exception {
        WordEntity wordEntity1 = new WordEntity("alelujah");
        wordEntity1.incrementPopularity();
        wordEntity1.setId(1L);

        WordEntity wordEntity2 = new WordEntity("aloo");
        wordEntity2.setId(1L);

        assertTrue("Equal by id only!" ,wordEntity1.equals(wordEntity2));

    }

    @Test
    public void testEquals2() throws Exception {
        WordEntity wordEntity1 = new WordEntity("alelujah");
        wordEntity1.setId(1L);

        WordEntity wordEntity2 = new WordEntity("alelujah");
        wordEntity1.setId(2L);

        assertFalse(wordEntity1.equals(wordEntity2));
    }

    @Test
    public void compareTo() throws Exception {
        WordEntity one = new WordEntity();
        one.setPopularity(12);

        WordEntity two = new WordEntity();
        two.setPopularity(12000);

        WordEntity three = new WordEntity();
        two.setPopularity(12001);

        List<WordEntity> list = Arrays.asList(one, three, two);
        Collections.sort(list);

        assertEquals(three, list.get(0));
        assertEquals(two, list.get(1));
        assertEquals(one, list.get(2));
    }



}
