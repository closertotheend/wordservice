package com.wordservice.mvc.dao;

import com.wordservice.mvc.model.NullWordEntity;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.repository.WordRelationshipRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class WordRelationshipDAO {
    private static final Logger logger = LogManager
            .getLogger(WordRelationshipDAO.class.getName());

    @Autowired
    private Neo4jTemplate template;

    @Autowired
    private WordRelationshipRepository wordRelationshipRepository;

    public WordRelationship save(WordRelationship wordRelationship) {
        return template.save(wordRelationship);
    }


    public WordRelationship getRelationshipBetween(WordEntity prelast, WordEntity last) {
        return template.getRelationshipBetween(prelast, last,
                WordRelationship.class, WordRelationship.relationshipType);
    }

    /*
    * Maybe caching could help a lot?
    * **/
    public Iterable<WordRelationship> getRelationshipsBetweenAsIterable(WordEntity prelast, WordEntity last) {
        Iterable<WordRelationship> relationshipsBetween = template.getRelationshipsBetween(prelast, last,
                WordRelationship.class, WordRelationship.relationshipType);
        if (relationshipsBetween == null) {
            return Collections.emptyList();
        }
        return relationshipsBetween;
    }

    public List<WordRelationship> getRelationshipsBetweenAsList(WordEntity prelast, WordEntity last) {
        List<WordRelationship> wordRelationships = new ArrayList<>();
        for (WordRelationship wordRelationship : getRelationshipsBetweenAsIterable(prelast, last)) {
            wordRelationships.add(wordRelationship);
        }
        return wordRelationships;
    }

    public List<WordRelationship> getRelationshipsBetweenAsList(WordEntity preprelast, WordEntity prelast, WordEntity last) {
        Set<WordRelationship> tuple = wordRelationshipRepository.getTuple(preprelast.getId(), prelast.getId(), last.getId());
        List<WordRelationship> wordRelationships = new ArrayList<>(tuple);
        Collections.sort(wordRelationships);
        return wordRelationships;
    }

    public WordRelationship getRelationshipBetween(WordEntity first, WordEntity second, WordEntity third, WordEntity fourth) {
        return wordRelationshipRepository.getTuple(first.getId(),second.getId(),third.getId(),fourth.getId());
    }

    public WordRelationship createOrIncrementPopularityOfWordRelationshipTuple(WordEntity first, WordEntity second, WordEntity third, WordEntity fourth) {
        long startTime = System.currentTimeMillis();


        WordRelationship exactTuple = getRelationshipBetween(first, second, third, fourth);

        if (exactTuple == null) {
            exactTuple = new WordRelationship(first, second, third, fourth);
        } else {
            exactTuple.incrementPopularity();
        }

        exactTuple = save(exactTuple);

        logger.info("Elapsed time for wordRelationshipTuple " + exactTuple + " operations is " + (System.currentTimeMillis() - startTime));

        return exactTuple;
    }

    public List<WordRelationship> saveWordRelationshipTuples(List<WordEntity> wordEntities) {
        List<WordRelationship> wordRelationships = new ArrayList<>();

        for (int i = 0; i < wordEntities.size(); i++) {
            WordRelationship wordRelationship = null;

            if (wordEntities.size() - i == 1) {
                break;
            }

            if (wordEntities.size() - i == 2) {
                wordRelationship = createOrIncrementPopularityOfWordRelationshipTuple(
                        wordEntities.get(i), wordEntities.get(i + 1), new NullWordEntity(), new NullWordEntity());
            }

            if (wordEntities.size() - i == 3) {
                wordRelationship = createOrIncrementPopularityOfWordRelationshipTuple(
                        wordEntities.get(i), wordEntities.get(i + 1), wordEntities.get(i + 2), new NullWordEntity());
            }

            if (wordEntities.size() - i > 3) {
                wordRelationship = createOrIncrementPopularityOfWordRelationshipTuple(
                        wordEntities.get(i), wordEntities.get(i + 1), wordEntities.get(i + 2), wordEntities.get(i + 3));

            }

            wordRelationships.add(wordRelationship);
        }
        return wordRelationships;
    }

}
