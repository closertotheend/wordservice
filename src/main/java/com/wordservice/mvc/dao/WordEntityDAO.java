package com.wordservice.mvc.dao;


import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordEntityRepository;
import com.wordservice.mvc.util.CleanUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertTrue;

@Service
public class WordEntityDAO {

    private static final Logger logger = LogManager
            .getLogger(WordEntityDAO.class.getName());

    @Autowired
    private WordEntityRepository wordEntityRepository;

    public WordEntity findById(long id) {
        return wordEntityRepository.findOne(id);
    }

    public WordEntity findByWordViaIndexAndRegex(String word) {

        WordEntity matchViaIndex = findByWordViaIndex(word);
        if (matchViaIndex == null) {
            try {
                long startTime = System.currentTimeMillis();
                WordEntity noIndexMatch = wordEntityRepository.findByWordWithoutFastIndex(word);
                long estimatedTime = System.currentTimeMillis() - startTime;
                System.err.println("RegexMatch " + estimatedTime);
                return noIndexMatch;
            } catch (Exception e) {
                System.err.println("Word which caused fail - " + word);
                e.printStackTrace();
            }

        }
        return matchViaIndex;
    }

    public WordEntity findByWordViaIndex(String word) {
        if (!word.trim().isEmpty() && !word.contains(":")) {
            return wordEntityRepository.findByWordOptimized(word);
        }
        return null;
    }

    public List<WordEntity> findByWordStartingWithViaIndex(String sequence) {
        try {
            Iterable<WordEntity> allCaseWords = wordEntityRepository.findByWordStartingWithOrderByPopularityDesc(sequence);
            List<WordEntity> result = new ArrayList<>();
            for (WordEntity someWord : allCaseWords) {
                if (someWord.getWord().contains(sequence) && result.size() < 20) {
                    result.add(someWord);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        //  List<WordEntity> allCaseWords = wordEntityRepository.findByWordRegexOrderByPopularity(sequence + ".*");
        //  List<WordEntity> result = new ArrayList<>(allCaseWords.size());
    }

    public List<WordEntity> findByWordContainingViaIndex(String sequence) {
        Iterable<WordEntity> byWordContainingOrderByPopularityDesc = wordEntityRepository.findByWordContainingOrderByPopularityDesc(sequence);
        List<WordEntity> result = new ArrayList<>();
        for (WordEntity someWord : byWordContainingOrderByPopularityDesc) {
            if (someWord.getWord().contains(sequence) && result.size() < 20) {
                result.add(someWord);
            }
        }
        return result;
        // return wordEntityRepository.findByWordRegexOrderByPopularity(".*" + sequence + ".*");
    }

    public WordEntity getOrCreateWordEntity(String word) {
        long startTime = System.currentTimeMillis();

        WordEntity wordEntity = findByWordViaIndex(word);
        if (wordEntity == null) {
            wordEntity = new WordEntity(word);
        } else {
            wordEntity.incrementPopularity();
        }
        wordEntity = wordEntityRepository.save(wordEntity);

        logger.info("Elapsed time for word " + word + " operations is " + (System.currentTimeMillis() - startTime));

        return wordEntity;
    }
}
