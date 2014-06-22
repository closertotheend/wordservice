package com.wordservice.mvc.service.wordsaver;

import com.wordservice.mvc.model.Sentence;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.model.WordTuple;
import com.wordservice.mvc.repository.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class WordEntitySaverService {
    private static final Logger logger = LogManager
            .getLogger(WordEntitySaverService.class.getName());

    @Autowired
    private  SaverService saverService;

    @Autowired
    private SentenceRepository sentenceRepository;


    @Autowired
    private WordTupleRepository wordTupleRepository;

    public void saveToRepo(String text) {
        List<String> sentences = TextToSentences.transform(text);
        for (String sentence : sentences) {
            List<String> words = SentencesToWords.transform(sentence);
            saveToRepo(words);
        }

        logger.info("UPLOAD FINISHED!");

    }

    void saveToRepo(List<String> words) {
        WordEntity wordEntity1 = saverService.getOrCreateWordEntity(words.get(0));
        Sentence sentence = new Sentence();
        int sizeOfSentence = words.size();
        for (int i = 0; i < sizeOfSentence - 1; i++) {
            if (sizeOfSentence == 2) {
                WordEntity wordEntity2 = saverService.getOrCreateWordEntity(words.get(i + 1));
                WordRelationship wordRelationship = saverService.createOrIncrementPopularityOfRelationship(wordEntity1, wordEntity2);
                sentence.getWordRelationships().add(wordRelationship.getId());
                wordEntity1 = wordEntity2;
            } else if (sizeOfSentence == 3) {
                WordEntity wordEntity2 = saverService.getOrCreateWordEntity(words.get(i + 1));
                WordEntity wordEntity3 = saverService.getOrCreateWordEntity(words.get(i + 2));

                WordRelationship wordRelationship1 = saverService.createOrIncrementPopularityOfRelationship(wordEntity1, wordEntity2);
                WordRelationship wordRelationship2 = saverService.createOrIncrementPopularityOfRelationship(wordEntity2, wordEntity3);

                sentence.getWordRelationships().add(wordRelationship1.getId());
                sentence.getWordRelationships().add(wordRelationship2.getId());

                wordTupleRepository.save(new WordTuple(wordRelationship1.getId(), wordRelationship2.getId()));
                break;
            } else {
                WordEntity wordEntity2 = saverService.getOrCreateWordEntity(words.get(i + 1));
                WordRelationship wordRelationship = saverService.createOrIncrementPopularityOfRelationship(wordEntity1, wordEntity2);
                sentence.getWordRelationships().add(wordRelationship.getId());
                wordEntity1 = wordEntity2;
            }
        }
        sentenceRepository.save(sentence);

    }

}
