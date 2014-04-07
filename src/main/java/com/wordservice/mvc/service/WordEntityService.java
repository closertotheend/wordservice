package com.wordservice.mvc.service;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ilja on 4/6/2014.
 */
@Service
public class WordEntityService {
    @Autowired
    Neo4jTemplate template;

    @Autowired
    WordRepository wordRepository;

    public void saveToRepo(String text) {
        List<String> sentences = TextToSentences.transform(text);
        for (String sentence : sentences) {
            List<String> words = SentencesToWords.transform(sentence);
            saveToRepo(words);
        }
    }

    public void saveToRepo(List<String> wordEntities) {
        for (int i = 0; i + 1 < wordEntities.size(); i++) {
            WordEntity wordEntity1 = getOrCreateWordEntity(wordEntities, i);
            WordEntity wordEntity2 = getOrCreateWordEntity(wordEntities, i + 1);

            createOrIncrementPopularityOfRelationship(wordEntity1, wordEntity2);
        }
    }

    private WordEntity getOrCreateWordEntity(List<String> wordEntities, int i) {
        String word2 = wordEntities.get(i);
        WordEntity wordEntity2 = wordRepository.findByWord(word2);
        if(wordEntity2==null){
            wordEntity2 = new WordEntity(word2);
            wordRepository.save(wordEntity2);
        }
        return wordEntity2;
    }

    private void createOrIncrementPopularityOfRelationship(WordEntity wordEntity1, WordEntity wordEntity2) {

        WordRelationship relationshipBetween = template.getRelationshipBetween(wordEntity1, wordEntity2 ,
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
