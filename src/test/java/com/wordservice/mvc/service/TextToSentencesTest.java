package com.wordservice.mvc.service;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by ilja on 4/7/2014.
 */
public class TextToSentencesTest {
    @Test
    public void testTransform() throws Exception {
        String source = "This is a test. This is a T.L.A. test. Now with a Dr. in it.";
        List<String> sentences = TextToSentences.transform(source);

        assertEquals("This is a test.", sentences.get(0));
        assertEquals("This is a T.L.A. test.", sentences.get(1));
        assertEquals("Now with a Dr. in it.", sentences.get(2));
        assertEquals(3, sentences.size());
    }
}
