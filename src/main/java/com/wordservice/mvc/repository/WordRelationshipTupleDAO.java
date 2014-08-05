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
        return template.getRelationshipsBetween(prelast, last,
                WordRelationshipTuple.class, WordRelationshipTuple.relationshipType);
    }

    public List<WordRelationshipTuple> getRelationshipsBetweenAsList(WordEntity prelast, WordEntity last) {
        Iterable<WordRelationshipTuple> relationshipsBetweenAsIterable = getRelationshipsBetweenAsIterable(prelast, last);
        List<WordRelationshipTuple> wordRelationshipTuples = new ArrayList<WordRelationshipTuple>();
        for (WordRelationshipTuple wordRelationshipTuple : relationshipsBetweenAsIterable) {
            wordRelationshipTuples.add(wordRelationshipTuple);
        }
        return  wordRelationshipTuples;
    }

    public WordRelationshipTuple createOrIncrementPopularityOfWordRelationshipTuple(WordEntity first, WordEntity second, WordEntity third, WordEntity fourth) {
        long startTime = System.currentTimeMillis();

        List<WordRelationshipTuple> wordRelationshipTuple = getRelationshipsBetweenAsList(first, second);

        WordRelationshipTuple exactRelationshipTupleIsFound = null;
        for (WordRelationshipTuple relationshipTuple : wordRelationshipTuple) {
            if(relationshipTuple.getThird() == third.getId() || relationshipTuple.getFourth() == fourth.getId()){
                exactRelationshipTupleIsFound = relationshipTuple;
                break;
            }
        }

        if (exactRelationshipTupleIsFound == null) {
            exactRelationshipTupleIsFound = new WordRelationshipTuple(first, second, third, fourth);
        } else {
            exactRelationshipTupleIsFound.incrementPopularity();
        }

        exactRelationshipTupleIsFound = save(exactRelationshipTupleIsFound);

        logger.info("Elapsed time for wordRelationshipTuple " + wordRelationshipTuple + " operations is " + (System.currentTimeMillis() - startTime));

        return exactRelationshipTupleIsFound;
    }


    public WordRelationship findOne(Long id) {
        return template.findOne(id, WordRelationship.class);
    }

    public EndResult<WordRelationship> findAll(){
        return template.findAll(WordRelationship.class);
    }

}
