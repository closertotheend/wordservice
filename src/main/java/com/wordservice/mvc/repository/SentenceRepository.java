package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.Sentence;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SentenceRepository extends GraphRepository<Sentence> {

    @Query("START sentence=node(*) " +
            "WHERE has(sentence.wordRelationships) AND (str({id}) IN sentence.wordRelationships) " +
            "RETURN sentence " )
    List<Sentence> getSentencesWithRelationshipId(@Param("id") Long id);

}
