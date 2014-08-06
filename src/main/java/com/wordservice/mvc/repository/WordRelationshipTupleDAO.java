package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.model.WordRelationshipTuple;
import com.wordservice.mvc.service.wordsaver.TextSaverService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class WordRelationshipTupleDAO {
    private static final Logger logger = LogManager
            .getLogger(WordRelationshipTupleDAO.class.getName());

    @Autowired
    private Neo4jTemplate template;

    public WordRelationshipTuple save(WordRelationshipTuple wordRelationship) {
        return template.save(wordRelationship);
    }


    public WordRelationshipTuple getRelationshipBetween(WordEntity prelast, WordEntity last) {
        return template.getRelationshipBetween(prelast, last,
                WordRelationshipTuple.class, WordRelationshipTuple.relationshipType);
    }

    public Iterable<WordRelationshipTuple> getRelationshipsBetweenAsIterable(WordEntity prelast, WordEntity last) {
        Iterable<WordRelationshipTuple> relationshipsBetween = template.getRelationshipsBetween(prelast, last,
                WordRelationshipTuple.class, WordRelationshipTuple.relationshipType);
        if (relationshipsBetween == null) {
            return Collections.emptyList();
        }
        return relationshipsBetween;
    }

    public List<WordRelationshipTuple> getRelationshipsBetweenAsList(WordEntity prelast, WordEntity last) {
        Iterable<WordRelationshipTuple> relationshipsBetweenAsIterable = getRelationshipsBetweenAsIterable(prelast, last);
        List<WordRelationshipTuple> wordRelationshipTuples = new ArrayList<>();
        for (WordRelationshipTuple wordRelationshipTuple : relationshipsBetweenAsIterable) {
            wordRelationshipTuples.add(wordRelationshipTuple);
        }
        return wordRelationshipTuples;
    }

    public WordRelationshipTuple createOrIncrementPopularityOfWordRelationshipTuple(WordEntity first, WordEntity second, WordEntity third, WordEntity fourth) {
        long startTime = System.currentTimeMillis();

        List<WordRelationshipTuple> wordRelationshipTuple = getRelationshipsBetweenAsList(first, second);

        WordRelationshipTuple exactTuple = null;
        for (WordRelationshipTuple relationshipTuple : wordRelationshipTuple) {
            if (relationshipTuple.getThird() == third.getId() || relationshipTuple.getFourth() == fourth.getId()) {
                exactTuple = relationshipTuple;
                break;
            }
        }

        if (exactTuple == null) {
            exactTuple = new WordRelationshipTuple(first, second, third, fourth);
        } else {
            exactTuple.incrementPopularity();
        }

        exactTuple = save(exactTuple);

        logger.info("Elapsed time for wordRelationshipTuple " + wordRelationshipTuple + " operations is " + (System.currentTimeMillis() - startTime));

        return exactTuple;
    }


    public WordRelationshipTuple findOne(Long id) {
        return template.findOne(id, WordRelationshipTuple.class);
    }

    public EndResult<WordRelationshipTuple> findAll() {
        return template.findAll(WordRelationshipTuple.class);
    }

}
