package com.wordservice.mvc.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class WordRelationshipTupleTest {

    @Test
    public void testCompareTo() throws Exception {

        WordRelationshipTuple one = new WordRelationshipTuple();
        one.setPopularity(12);

        WordRelationshipTuple two = new WordRelationshipTuple();
        two.setPopularity(12000);

        WordRelationshipTuple three = new WordRelationshipTuple();
        two.setPopularity(12001);

        List<WordRelationshipTuple> list = Arrays.asList(one, three, two);
        Collections.sort(list);

        assertEquals(three, list.get(0));
        assertEquals(two, list.get(1));
        assertEquals(one, list.get(2));


    }
}