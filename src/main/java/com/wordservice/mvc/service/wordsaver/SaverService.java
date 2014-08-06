package com.wordservice.mvc.service.wordsaver;


import com.wordservice.mvc.dao.WordEntityDAO;
import com.wordservice.mvc.dao.WordRelationshipTupleDAO;
import com.wordservice.mvc.model.*;
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
    private WordEntityRepository wordEntityRepository;

    @Autowired
    private WordEntityDAO wordEntityDAO;

    @Autowired
    private WordRelationshipTupleDAO wordRelationshipTupleDAO;

    WordEntity getOrCreateWordEntity(String word) {
        long startTime = System.currentTimeMillis();

        WordEntity wordEntity = wordEntityDAO.findByWord(word);
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
