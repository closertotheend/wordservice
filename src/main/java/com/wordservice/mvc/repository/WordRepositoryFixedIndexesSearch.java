package com.wordservice.mvc.repository;


import com.wordservice.mvc.model.WordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WordRepositoryFixedIndexesSearch {

    @Autowired
    private WordRepository wordRepository;

    public WordEntity findByWord(String word) {
        List<WordEntity> allCaseWords = Collections.emptyList();

        if (!word.trim().isEmpty()) {
            try {
                allCaseWords = wordRepository.findByWord(word);
            } catch (Exception e) {
                e.printStackTrace();
                allCaseWords = Collections.emptyList();
            }
        }

        for (WordEntity someWord : allCaseWords) {
            if (someWord.getWord().equals(word)) {
                return someWord;
            }
        }
        return null;
    }


    public List<WordEntity> findByWordStartingWith(String sequence){
        try {
            List<WordEntity> allCaseWords = wordRepository.findByWordRegexOrderByPopularity(sequence + ".*");
            List<WordEntity> result = new ArrayList<>(allCaseWords.size());
            for (WordEntity someWord : allCaseWords) {
                if(someWord.getWord().contains(sequence)){
                    result.add(someWord);
                }
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<WordEntity> findByWordContaining(String sequence){
        return wordRepository.findByWordRegexOrderByPopularity(".*" + sequence + ".*");
    }
}
