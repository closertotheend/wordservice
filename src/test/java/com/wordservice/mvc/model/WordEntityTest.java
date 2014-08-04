package com.wordservice.mvc.model;

import org.junit.Test;

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
}
