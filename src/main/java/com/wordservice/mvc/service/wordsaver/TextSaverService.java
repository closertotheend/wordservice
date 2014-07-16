package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.WordTupleRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TextSaverService {
    private static final Logger logger = LogManager
            .getLogger(TextSaverService.class.getName());

    @Autowired
    private SaverService saverService;

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
        saveWordTriTuples(wordRelationships);
        return true;
    }



    private void saveWordTuples(List<WordRelationship> wordRelationships) {
        for (int i = 0; i < wordRelationships.size() - 1; i++) {
            saverService.createOrIncrementPopularityOfWordTuple(wordRelationships.get(i), wordRelationships.get(i+1));
        }
    }

    private void saveWordTriTuples(List<WordRelationship> wordRelationships) {
        for (int i = 0; i < wordRelationships.size() - 2; i++) {
            saverService.createOrIncrementPopularityOfWordTriTuple(wordRelationships.get(i), wordRelationships.get(i+1), wordRelationships.get(i+2));
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
