package com.wordservice.mvc.dao;


import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.repository.WordEntityRepository;
import com.wordservice.mvc.util.CleanUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WordEntityDAO {

    private static final Logger logger = LogManager
            .getLogger(WordEntityDAO.class.getName());

    @Autowired
    private WordEntityRepository wordEntityRepository;

    public WordEntity findById(long id) {
        return wordEntityRepository.findOne(id);
    }

    public WordEntity findByWord(String word) {
        List<WordEntity> allCaseWords = Collections.emptyList();

        if (!word.trim().isEmpty()) {
            try {
                if (!CleanUtil.hasNonWordCharacter(word)) {
                    allCaseWords = wordEntityRepository.findByWord(CleanUtil.clean(word));
                }
            } catch (Exception e) {
                System.err.println("WORD WAS " + word);
                e.printStackTrace();
                allCaseWords = Collections.emptyList();
            }

//            try {
//                if(allCaseWords.size()==0){
//                    allCaseWords = wordEntityRepository.findByWordRegexOrderByPopularity(word);
//                }
//            } catch (Exception e) {
//                System.err.println("WORD WAS " + word);
//                e.printStackTrace();
//                allCaseWords = Collections.emptyList();
//            }

        }

        for (WordEntity someWord : allCaseWords) {
            if (someWord.getWord().equals(word)) {
                return someWord;
            }
        }
        return null;
    }


    public List<WordEntity> findByWordStartingWith(String sequence) {
        try {
            List<WordEntity> allCaseWords = wordEntityRepository.findByWordRegexOrderByPopularity(sequence + ".*");
            List<WordEntity> result = new ArrayList<>(allCaseWords.size());
            for (WordEntity someWord : allCaseWords) {
                if (someWord.getWord().contains(sequence)) {
                    result.add(someWord);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<WordEntity> findByWordContaining(String sequence) {
        return wordEntityRepository.findByWordRegexOrderByPopularity(".*" + sequence + ".*");
    }

    public WordEntity getOrCreateWordEntity(String word) {
        long startTime = System.currentTimeMillis();

        WordEntity wordEntity = findByWord(word);
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
