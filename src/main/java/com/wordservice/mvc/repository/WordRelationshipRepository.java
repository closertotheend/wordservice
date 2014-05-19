package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WordRelationshipRepository {

    @Autowired
    private Neo4jTemplate template;

    public WordRelationship save(WordRelationship wordRelationship) {
        return template.save(wordRelationship);
    }


    public WordRelationship getRelationshipBetween(WordEntity wordEntity1, WordEntity wordEntity2) {
        return template.getRelationshipBetween(wordEntity1, wordEntity2,
                WordRelationship.class, WordRelationship.relationshipType);
    }

    public WordRelationship findOne(Long id) {
        return template.findOne(id, WordRelationship.class);
    }

    public EndResult<WordRelationship> findAll(){
        return template.findAll(WordRelationship.class);
    }

}
