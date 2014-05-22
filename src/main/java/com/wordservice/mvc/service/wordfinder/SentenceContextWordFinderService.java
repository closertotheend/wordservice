package com.wordservice.mvc.service.wordfinder;

import com.wordservice.mvc.model.Sentence;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.SentenceRepository;
import com.wordservice.mvc.repository.WordRelationshipRepository;
import com.wordservice.mvc.repository.WordRepository;
import com.wordservice.mvc.service.wordsaver.SentencesToWords;
import com.wordservice.mvc.service.wordsaver.TextToSentences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static junit.framework.Assert.assertEquals;


@Service
public class SentenceContextWordFinderService {

    @Autowired
    private WordRelationshipRepository wordRelationshipRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private SentenceRepository sentenceRepository;

    public List<String> getNextWords(String word1, String word2){
        WordEntity firstWord = wordRepository.findByWord(word1);
        if (firstWord == null) return Collections.emptyList();
        WordEntity secondWord = wordRepository.findByWord(word2);
        if (secondWord == null) return Collections.emptyList();
        WordRelationship relationshipBetween1and2 = wordRelationshipRepository.getRelationshipBetween(firstWord, secondWord);;
        if (relationshipBetween1and2 == null) return Collections.emptyList();

        List<Sentence> sentencesWithRelationshipId = sentenceRepository.getSentencesWithRelationshipId(relationshipBetween1and2.getId());

        List<String> words = new ArrayList<>();
        for (Sentence sentence : sentencesWithRelationshipId) {
            int indexOfRelationship = sentence.getWordRelationships().indexOf(relationshipBetween1and2.getId());
            if (relationshipIsNotLastInSentece(sentence, indexOfRelationship)){
                Long idOfNextRelationship = sentence.getWordRelationships().get(indexOfRelationship + 1);
                WordRelationship nextWordRelationship = wordRelationshipRepository.findOne(idOfNextRelationship);
                words.add(wordRepository.findOne(
                        nextWordRelationship.getSecondWord().getId()
                ).getWord());
            }
        }

        return words;
    }

    private boolean relationshipIsNotLastInSentece(Sentence sentence, int indexOfRelationship) {
        return sentence.getWordRelationships().size() > indexOfRelationship + 1;
    }

}
