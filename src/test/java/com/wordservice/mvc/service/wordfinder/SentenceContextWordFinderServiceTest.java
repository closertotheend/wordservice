package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.model.Sentence;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.SentenceRepository;
import com.wordservice.mvc.repository.WordRelationshipRepository;
import com.wordservice.mvc.repository.WordRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SentenceContextWordFinderServiceTest {

    @InjectMocks
    private SentenceContextWordFinderService sentenceContextWordFinderService;

    @Mock
    private WordRelationshipRepository wordRelationshipRepository;

    @Mock
    private WordRepository wordRepository;

    @Mock
    private SentenceRepository sentenceRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetNextWords() throws Exception {
        WordEntity thiss = new WordEntity();
        thiss.setWord("This");
        thiss.setId(1l);
        WordEntity is = new WordEntity();
        is.setId(2l);
        is.setWord("is");
        WordEntity cat = new WordEntity();
        cat.setId(3l);
        cat.setWord("cat");
        WordEntity dog = new WordEntity();
        dog.setWord("dog");
        dog.setId(4l);

        WordRelationship wordRelationship123 = new WordRelationship();
        wordRelationship123.setId(123l);
        wordRelationship123.setStartWord(thiss);
        wordRelationship123.setSecondWord(is);

        WordRelationship wordRelationship777 = new WordRelationship();
        wordRelationship777.setId(777l);
        wordRelationship777.setStartWord(is);
        wordRelationship777.setSecondWord(cat);

        WordRelationship wordRelationship888 = new WordRelationship();
        wordRelationship888.setId(888l);
        wordRelationship888.setStartWord(is);
        wordRelationship888.setSecondWord(dog);

        Sentence sentence1 = new Sentence();
        sentence1.getWordRelationships().add(123l);
        sentence1.getWordRelationships().add(777l);

        Sentence sentence2 = new Sentence();
        sentence2.getWordRelationships().add(123l);
        sentence2.getWordRelationships().add(888l);

        when(wordRepository.findByWord("This")).thenReturn(thiss);
        when(wordRepository.findByWord("is")).thenReturn(is);

        when(wordRepository.findOne(cat.getId())).thenReturn(cat);
        when(wordRepository.findOne(dog.getId())).thenReturn(dog);
        when(wordRepository.findOne(thiss.getId())).thenReturn(thiss);
        when(wordRepository.findOne(is.getId())).thenReturn(is);

        when(wordRelationshipRepository.findOne(wordRelationship777.getId())).thenReturn(wordRelationship777);
        when(wordRelationshipRepository.findOne(wordRelationship888.getId())).thenReturn(wordRelationship888);

        when(wordRelationshipRepository.getRelationshipBetween(thiss, is)).thenReturn(wordRelationship123);
        when(sentenceRepository.getSentencesWithRelationshipId(wordRelationship123.getId())).thenReturn(Arrays.asList(sentence1, sentence2));

        List<String> sentences = sentenceContextWordFinderService.getNextWords("This", "is");
        assertTrue(sentences.contains("cat"));
        assertTrue(sentences.contains("dog"));
        assertEquals(2,sentences.size());
    }
}