package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.dao.WordEntityDAO;
import com.wordservice.mvc.model.NullWordEntity;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationshipTuple;
import com.wordservice.mvc.dao.WordRelationshipTupleDAO;
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
    private WordEntityDAO wordEntityDAO;

    @Autowired
    private WordRelationshipTupleDAO wordRelationshipTupleDAO;

    public void saveToRepo(String text) {
        List<String> sentences = TextToSentences.transform(text);
        for (String sentence : sentences) {
            List<String> words = SentencesToWords.transform(sentence);
            saveToRepo(words);
        }
        logger.info("UPLOAD FINISHED!");
    }


    public boolean saveToRepo(List<String> words) {
        List<WordEntity> wordEntities = saveWordEntities(words);
        wordRelationshipTupleDAO.saveWordRelationshipTuples(wordEntities);
        return true;
    }


    private List<WordEntity> saveWordEntities(List<String> words) {
        List<WordEntity> wordEntities = new ArrayList<>();
        for (String word : words) {
            WordEntity wordEntity = wordEntityDAO.getOrCreateWordEntity(clean(word));
            wordEntities.add(wordEntity);
        }
        return wordEntities;
    }

    private static String clean(String word) {
        return word.replaceAll("/[^A-Za-z0-9 ]/", "").trim();
    }


}
