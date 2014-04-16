package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.WordRelationshipRepositoryImpl;
import com.wordservice.mvc.repository.WordRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    WordRelationshipRepositoryImpl wordRelationshipRepository;

    @Autowired
    WordRepositoryImpl wordRepositoryCached;

    @Transactional
    public void saveToRepo(String text) {
        List<String> sentences = TextToSentences.transform(text);
        for (String sentence : sentences) {
            List<String> words = SentencesToWords.transform(sentence);
            saveToRepo(words);
        }
        logger.info("UPLOAD FINISHED!");
    }

    private void saveToRepo(List<String> words) {
        if (words.size() >= 2) {
            WordEntity wordEntity1 = getOrCreateWordEntity(words, 0);
            for (int i = 0; i + 1 < words.size(); i++) {
                WordEntity wordEntity2 = getOrCreateWordEntity(words, i + 1);
                createOrIncrementPopularityOfRelationship(wordEntity1, wordEntity2);
                wordEntity1 = wordEntity2;
            }
        }

    }

    private WordEntity getOrCreateWordEntity(List<String> wordEntities, int wordIndex) {
        long startTime = System.currentTimeMillis();

        String word = wordEntities.get(wordIndex);
        WordEntity wordEntity = wordRepositoryCached.findByWord(word);
        if (wordEntity == null) {
            wordEntity = new WordEntity(word);
            wordRepositoryCached.save(wordEntity);
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        logger.info("Elapsed time for word {} operations is " + estimatedTime, word);

        return wordEntity;
    }

    private void createOrIncrementPopularityOfRelationship(WordEntity wordEntity1, WordEntity wordEntity2) {
        long startTime = System.currentTimeMillis();

        WordRelationship relationshipBetween = wordRelationshipRepository.getRelationshipBetween(wordEntity1, wordEntity2 );
        if (relationshipBetween == null) {
            WordRelationship wordRelationship = new WordRelationship(wordEntity1, wordEntity2);
            wordRelationshipRepository.save(wordRelationship);
        } else {
            relationshipBetween.incrementPopularity();
            wordRelationshipRepository.save(relationshipBetween);
        }

        long estimatedTime = System.currentTimeMillis() - startTime;
        logger.info("Elapsed time for relationship {} operations is " + estimatedTime, startTime);
    }

}
