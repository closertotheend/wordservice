package com.wordservice.mvc.service;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ilja on 4/6/2014.
 */
@Service
public class WordEntitySaverService {
    private static final Logger logger = LoggerFactory
            .getLogger(WordEntitySaverService.class);

    @Autowired
    Neo4jTemplate template;

    @Autowired
    WordRepository wordRepository;

    @Transactional
    public void saveToRepo(String text) {
        List<String> sentences = TextToSentences.transform(text);
        for (String sentence : sentences) {
            List<String> words = SentencesToWords.transform(sentence);
            saveToRepo(words);
        }
        logger.info("UPLOAD FINISHED!");
    }

    private void saveToRepo(List<String> wordEntities) {
        for (int i = 0; i + 1 < wordEntities.size(); i++) {

            WordEntity wordEntity1 = getOrCreateWordEntity(wordEntities, i);
            WordEntity wordEntity2 = getOrCreateWordEntity(wordEntities, i + 1);

            createOrIncrementPopularityOfRelationship(wordEntity1, wordEntity2);
        }
    }

    private WordEntity getOrCreateWordEntity(List<String> wordEntities, int wordIndex) {
        String word = wordEntities.get(wordIndex);

        long startTime = System.currentTimeMillis();

        WordEntity wordEntity = wordRepository.findByWord(word);
        if (wordEntity == null) {
            wordEntity = new WordEntity(word);
            wordRepository.save(wordEntity);
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        logger.info("Elapsed time for word {} operations is " + estimatedTime, word);

        return wordEntity;
    }

    private void createOrIncrementPopularityOfRelationship(WordEntity wordEntity1, WordEntity wordEntity2) {

        WordRelationship relationshipBetween = template.getRelationshipBetween(wordEntity1, wordEntity2,
                WordRelationship.class, WordRelationship.relationshipType);

        if (relationshipBetween == null) {
            WordRelationship wordRelationship = new WordRelationship(wordEntity1, wordEntity2);
            template.save(wordRelationship);
        } else {
            relationshipBetween.incrementPopularity();
            template.save(relationshipBetween);
        }
    }

}
