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

    @Test
    public void testName() throws Exception {
        int i, j;
        boolean not;

        System.out.println("P\t Q\t AND\t OR\t XOR\t NOT");

        i = 1; j= 1;


        System.out.print(i+"\t" +" "+ j + "\t" +" ");
        System.out.print((i&j) + "\t" + " "+ (i|j) + "\t"+" ");
        System.out.println((i^j) + "\t" + " "+ (i+1)%2 + "\t"+" ");

    }

//    public static final int fromBoolToInt(boolean bool){
//        if(bool == true){
//            return 1;
//        }else {
//            return 0;
//        }
//    }
}
