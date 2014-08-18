package com.wordservice.mvc.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class CleanUtilTest {

    @Test
    public void testClean() throws Exception {
        System.err.println(CleanUtil.clean("   asdasdas asdas12dasd    "));
    }

    @Test
    public void testHasNonWordCharacter() throws Exception {

    }

    @Test
    public void removeUnnecessaryTabsAndReturns() {
        String replaced = CleanUtil.removeSpecialCharacters("This is my text. \n An\nd here is a new \t line");
        assertFalse(replaced.contains("\n"));
        assertFalse(replaced.contains("\t"));
    }

    @Test
    public void removeNumbers() {
        String replaced = CleanUtil.removeNumbers("CHAPTER123");
        assertEquals("CHAPTER", replaced);
    }

    @Test
    public void removeSpecialCharacters() {
        String replaced1 = CleanUtil.removeSpecialCharacters("“Now Longfellow—” she was saying.");
        assertEquals("Now Longfellow she was saying.", replaced1);

        String replaced2 = CleanUtil.removeSpecialCharacters("“I must ’a’ missed ’em,” he announced.");
        assertEquals("I must a missed em, he announced.", replaced2);

        String replaced3 = CleanUtil.removeSpecialCharacters("Even I can see that, an’ I ain’t nobody");
        assertEquals("Even I can see that, an I aint nobody", replaced3);

        String replaced4 = CleanUtil.removeSpecialCharacters("Mr. Dorian Gray'second, sir,");
        assertEquals("Mr. Dorian Gray'second, sir,", replaced4);
    }

}