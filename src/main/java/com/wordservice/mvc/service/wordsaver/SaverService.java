package com.wordservice.mvc.service.wordsaver;


import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SaverService {
    private static final Logger logger = LogManager
            .getLogger(TextSaverService.class.getName());

    @Autowired
    private WordRelationshipRepository wordRelationshipRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordRepositoryFixedIndexesSearch wordRepositoryFixedIndexesSearch;


    WordEntity getOrCreateWordEntity(String word) {
        long startTime = System.currentTimeMillis();

        WordEntity wordEntity = wordRepositoryFixedIndexesSearch.findByWord(word);
        if (wordEntity == null) {
            wordEntity = new WordEntity(word);
        } else {
            wordEntity.incrementPopularity();
        }
        wordEntity = wordRepository.save(wordEntity);

        logger.info("Elapsed time for word " + word + " operations is " + (System.currentTimeMillis() - startTime));

        return wordEntity;
    }

    WordRelationship createOrIncrementPopularityOfRelationship(WordEntity wordEntity1, WordEntity wordEntity2) {
        long startTime = System.currentTimeMillis();

        WordRelationship relationshipBetween = wordRelationshipRepository.getRelationshipBetween(wordEntity1, wordEntity2);
        if (relationshipBetween == null) {
            WordRelationship wordRelationship = new WordRelationship(wordEntity1, wordEntity2);
            relationshipBetween = wordRelationshipRepository.save(wordRelationship);
        } else {
            relationshipBetween.incrementPopularity();
            relationshipBetween = wordRelationshipRepository.save(relationshipBetween);
        }

        logger.info("Elapsed time for relationship  operations is " + (System.currentTimeMillis() - startTime));

        return relationshipBetween;
    }
}
