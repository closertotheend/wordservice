package com.wordservice.mvc.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SentenceTest {
    @Test
    public void testEquals() throws Exception {
        Sentence sentenceNo1 = new Sentence();
        sentenceNo1.setId(1L);

        Sentence sentenceNo2 = new Sentence();
        sentenceNo2.setId(432L);

        assertFalse("Sentences should be matched by id only", sentenceNo1.equals(sentenceNo2));
    }

    @Test
    public void testEquals2() throws Exception {
        Sentence sentenceNo1 = new Sentence();
        sentenceNo1.setId(1L);
        sentenceNo1.setWordRelationships(new ArrayList<Long>());

        Sentence sentenceNo2 = new Sentence();
        sentenceNo2.setId(1L);
        sentenceNo2.setWordRelationships(new ArrayList<Long>());

        assertTrue("Sentences are matched by id only", sentenceNo1.equals(sentenceNo2));
    }
}
