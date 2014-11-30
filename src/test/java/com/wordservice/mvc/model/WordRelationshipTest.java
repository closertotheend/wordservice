package com.wordservice.mvc.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class WordRelationshipTest {

    @Test
    public void testCompareTo() throws Exception {

        WordRelationship one = new WordRelationship();
        one.setPopularity(12);

        WordRelationship two = new WordRelationship();
        two.setPopularity(12000);

        WordRelationship three = new WordRelationship();
        two.setPopularity(12001);

        List<WordRelationship> list = Arrays.asList(one, three, two);
        Collections.sort(list);

        assertEquals(three, list.get(0));
        assertEquals(two, list.get(1));
        assertEquals(one, list.get(2));


    }
}