package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.model.Sentence;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.SentenceRepository;
import com.wordservice.mvc.repository.WordRelationshipRepository;
import com.wordservice.mvc.repository.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WordEntitySaverService {
    private static final Logger logger = LoggerFactory
            .getLogger(WordEntitySaverService.class);

    @Autowired
    private WordRelationshipRepository wordRelationshipRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private SentenceRepository sentenceRepository;

    public void saveToRepo(String text) {
        List<String> sentences = TextToSentences.transform(text);
        for (String sentence : sentences) {
            List<String> words = SentencesToWords.transform(sentence);
            saveToRepo(words);
        }
        logger.info("UPLOAD FINISHED!");
    }

    void saveToRepo(List<String> words) {
        if (words.size() >= 2) {
            WordEntity wordEntity1 = getOrCreateWordEntity(words.get(0));
            Sentence sentence = new Sentence();
            for (int i = 0; i + 1 < words.size(); i++) {
                WordEntity wordEntity2 = getOrCreateWordEntity(words.get(i + 1));
                WordRelationship wordRelationship = createOrIncrementPopularityOfRelationship(wordEntity1, wordEntity2);
                sentence.getWordRelationships().add(wordRelationship.getId());
                wordEntity1 = wordEntity2;
            }
            sentenceRepository.save(sentence);
        }

    }

    WordEntity getOrCreateWordEntity(String word) {
        long startTime = System.currentTimeMillis();

        WordEntity wordEntity = wordRepository.findByWord(word);
        if (wordEntity == null) {
            wordEntity = new WordEntity(word);
        }else{
            wordEntity.incrementPopularity();
        }
        wordEntity = wordRepository.save(wordEntity);

        long estimatedTime = System.currentTimeMillis() - startTime;
        logger.info("Elapsed time for word {} operations is " + estimatedTime, word);

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

        long estimatedTime = System.currentTimeMillis() - startTime;
        logger.info("Elapsed time for relationship {} operations is " + estimatedTime, startTime);

        return relationshipBetween;
    }

}
