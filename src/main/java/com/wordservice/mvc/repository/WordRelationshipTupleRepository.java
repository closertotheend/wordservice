package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import com.wordservice.mvc.model.WordRelationshipTuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WordRelationshipTupleRepository {

    @Autowired
    private Neo4jTemplate template;

    public WordRelationshipTuple save(WordRelationshipTuple wordRelationship) {
        return template.save(wordRelationship);
    }


    public WordRelationshipTuple getRelationshipBetween(WordEntity prelast, WordEntity last) {
        return template.getRelationshipBetween(prelast, last,
                WordRelationshipTuple.class, WordRelationshipTuple.relationshipType);
    }

    public WordRelationship findOne(Long id) {
        return template.findOne(id, WordRelationship.class);
    }

    public EndResult<WordRelationship> findAll(){
        return template.findAll(WordRelationship.class);
    }

}
