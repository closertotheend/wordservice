package com.wordservice.mvc.model;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class WordRelationshipTest {

    @Test
    public void testEquals() throws Exception {
        WordRelationship wordRelationship1 = new WordRelationship(new WordEntity("Hello"), new WordEntity("World"));
        wordRelationship1.setId(1L);
        WordRelationship wordRelationship2 = new WordRelationship(new WordEntity("Hello"), new WordEntity("World"));
        wordRelationship2.setId(2L);
        assertFalse(wordRelationship1.equals(wordRelationship2));
    }

    @Test
    public void testEquals2() throws Exception {
        WordRelationship wordRelationship1 = new WordRelationship(new WordEntity("Hellggvvo"), new WordEntity("World"));
        wordRelationship1.incrementPopularity();
        wordRelationship1.setId(1L);
        WordRelationship wordRelationship2 = new WordRelationship(new WordEntity("Hello"), new WordEntity("Wggorld"));
        wordRelationship2.setId(1L);
        assertTrue("Equality by id only", wordRelationship1.equals(wordRelationship2));
    }
}
