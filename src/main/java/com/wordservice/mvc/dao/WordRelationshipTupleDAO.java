package com.wordservice.mvc.dao;

import com.wordservice.mvc.model.NullWordEntity;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationshipTuple;
import com.wordservice.mvc.repository.WordRelationshipRepository;
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
import java.util.Set;

@Service
@Transactional
public class WordRelationshipTupleDAO {
    private static final Logger logger = LogManager
            .getLogger(WordRelationshipTupleDAO.class.getName());

    @Autowired
    private Neo4jTemplate template;

    @Autowired
    private WordRelationshipRepository wordRelationshipRepository;

    public WordRelationshipTuple save(WordRelationshipTuple wordRelationship) {
        return template.save(wordRelationship);
    }


    public WordRelationshipTuple getRelationshipBetween(WordEntity prelast, WordEntity last) {
        return template.getRelationshipBetween(prelast, last,
                WordRelationshipTuple.class, WordRelationshipTuple.relationshipType);
    }

    /*
    * Maybe caching could help a lot?
    * **/
    public Iterable<WordRelationshipTuple> getRelationshipsBetweenAsIterable(WordEntity prelast, WordEntity last) {
        Iterable<WordRelationshipTuple> relationshipsBetween = template.getRelationshipsBetween(prelast, last,
                WordRelationshipTuple.class, WordRelationshipTuple.relationshipType);
        if (relationshipsBetween == null) {
            return Collections.emptyList();
        }
        return relationshipsBetween;
    }

    public List<WordRelationshipTuple> getRelationshipsBetweenAsList(WordEntity prelast, WordEntity last) {
        List<WordRelationshipTuple> wordRelationshipTuples = new ArrayList<>();
        for (WordRelationshipTuple wordRelationshipTuple : getRelationshipsBetweenAsIterable(prelast, last)) {
            wordRelationshipTuples.add(wordRelationshipTuple);
        }
        return wordRelationshipTuples;
    }

    public List<WordRelationshipTuple> getRelationshipsBetweenAsList(WordEntity preprelast, WordEntity prelast, WordEntity last) {
        Set<WordRelationshipTuple> tuple = wordRelationshipRepository.getTuple(preprelast.getId(), prelast.getId(), last.getId());
        List<WordRelationshipTuple> wordRelationshipTuples = new ArrayList<>(tuple);
        Collections.sort(wordRelationshipTuples);
        return wordRelationshipTuples;
    }

    public WordRelationshipTuple getRelationshipBetween(WordEntity first, WordEntity second, WordEntity third, WordEntity fourth) {
        return wordRelationshipRepository.getTuple(first.getId(),second.getId(),third.getId(),fourth.getId());
    }

    public WordRelationshipTuple createOrIncrementPopularityOfWordRelationshipTuple(WordEntity first, WordEntity second, WordEntity third, WordEntity fourth) {
        long startTime = System.currentTimeMillis();


        WordRelationshipTuple exactTuple = getRelationshipBetween(first, second, third, fourth);

        if (exactTuple == null) {
            exactTuple = new WordRelationshipTuple(first, second, third, fourth);
        } else {
            exactTuple.incrementPopularity();
        }

        exactTuple = save(exactTuple);

        logger.info("Elapsed time for wordRelationshipTuple " + exactTuple + " operations is " + (System.currentTimeMillis() - startTime));

        return exactTuple;
    }

    public List<WordRelationshipTuple> saveWordRelationshipTuples(List<WordEntity> wordEntities) {
        List<WordRelationshipTuple> wordRelationships = new ArrayList<>();

        for (int i = 0; i < wordEntities.size(); i++) {
            WordRelationshipTuple wordRelationship = null;

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


    public WordRelationshipTuple findOne(Long id) {
        return template.findOne(id, WordRelationshipTuple.class);
    }

    public EndResult<WordRelationshipTuple> findAll() {
        return template.findAll(WordRelationshipTuple.class);
    }

}
