package com.wordservice.mvc.util;

import com.wordservice.mvc.service.wordsaver.SentencesToWords;
import com.wordservice.mvc.service.wordsaver.TextToSentences;
import org.junit.Test;

import java.util.List;

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


    @Test
    public void prepareTextForSave() throws Exception {
        String s =
                CleanUtil.prepareTextForSave("Slovensko má pomerne hustú sieť osídlenia, nachádza sa tu 2 891 samostatných obcí, z toho je 138 miest (2009)");

        System.err.println(s);

        List<String> transform1 = SentencesToWords.transform(s);

        System.err.println(transform1);

        String s1 = CleanUtil.prepareTextForSave("Независимость         страны провозглашена 1 января 1993 года. На протяжении истории территория страны входила в состав многих держав и государственных образований, начиная от Государства Само в VII веке вплоть до Чехословакии в XX веке. В годы Второй мировой войны существовало зависимое от Третьего рейха словацкое государство, которое в 1945 году вновь стало частью Чехословакии.");

        System.err.println(s1);

        List<String> transform = SentencesToWords.transform(s1);

        System.err.println(transform);
    }

    @Test
    public void prepareTextForSave1() throws Exception {
        List<String> sentences = TextToSentences.transform("Столица — Братислава. Государственный язык — словацкий.\n" +
                "Унитарное государство, парламентская республика. По состоянию на июнь 2014 года пост президента занимает Андрей Киска, премьер-министра — Роберт Фицо. Подразделяется на 8 краёв.\n" +
                "Расположена в центре Европы. Континентальное государство, не имеющее выхода к морю. Имеет сухопутную границу с Украиной, Польшей, Чехией, Австрией и Венгрией.\n" +
                "Большая часть верующих (около 70 % населения) исповедует католицизм.\n" +
                "Индустриальная страна с динамично развивающейся экономикой. Объём ВВП за 2011 год составил 127,111 миллиардов долларов США (около 23 384 долларов США на душу населения). Денежная единица — евро.");
        for (String sentence : sentences) {
            List<String> words = SentencesToWords.transform(CleanUtil.prepareTextForSave(sentence));
            System.err.println(words);
        }
    }
}