package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.model.Sentence;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class TextSaverService {
    private static final Logger logger = LogManager
            .getLogger(TextSaverService.class.getName());

    @Autowired
    private SaverService saverService;

    @Autowired
    private SentenceRepository sentenceRepository;

    @Autowired
    private WordTupleRepository wordTupleRepository;

    public void saveToRepo(String text) {
        List<String> sentences = TextToSentences.transform(text);
        for (String sentence : sentences) {
            List<String> words = SentencesToWords.transform(sentence);
            saveToRepo(words);
        }
        logger.info("UPLOAD FINISHED!");
    }


    boolean saveToRepo(List<String> words) {
        List<WordEntity> wordEntities = saveWordEntities(words);
        List<WordRelationship> wordRelationships = saveWordRelationships(wordEntities);
        saveWordTuples(wordRelationships);
        saveSentence(wordRelationships);
        return true;
    }

    private void saveSentence(List<WordRelationship> wordRelationships) {
        if( wordRelationships.size()>0) {
            Sentence sentence = new Sentence();
            for (WordRelationship wordRelationship : wordRelationships) {
                sentence.getWordRelationships().add(wordRelationship.getId());
            }
            sentenceRepository.save(sentence);
        }
    }

    private void saveWordTuples(List<WordRelationship> wordRelationships) {
        for (int i = 0; i < wordRelationships.size() - 1; i++) {
            saverService.createOrIncrementPopularityOfWordTuple(wordRelationships.get(i), wordRelationships.get(i));
        }
    }

    private List<WordRelationship> saveWordRelationships(List<WordEntity> wordEntities) {
        List<WordRelationship> wordRelationships = new ArrayList<>();
        for (int i = 0; i < wordEntities.size() - 1; i++) {
            WordRelationship wordRelationship = saverService
                    .createOrIncrementPopularityOfRelationship(wordEntities.get(i), wordEntities.get(i + 1));
            wordRelationships.add(wordRelationship);
        }
        return wordRelationships;
    }

    private List<WordEntity> saveWordEntities(List<String> words) {
        List<WordEntity> wordEntities = new ArrayList<>();
        for (String word : words) {
            WordEntity wordEntity = saverService.getOrCreateWordEntity(word);
            wordEntities.add(wordEntity);
        }
        return wordEntities;
    }


}
