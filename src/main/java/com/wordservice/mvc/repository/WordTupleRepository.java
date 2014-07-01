package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.Sentence;
import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordTuple;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordTupleRepository extends GraphRepository<WordTuple> {


    @Query("START wordTuple= node(*) WHERE has(wordTuple.fWordRelationshipId) " +
            "AND wordTuple.fWordRelationshipId = {id1} AND wordTuple.secondWordRelationshipId = {id2}" +
            "RETURN wordTuple ORDER BY wordTuple.popularity DESC LIMIT 10")
    WordTuple getTupleWithRelationShipIds(@Param("id1") Long id1, @Param("id2") Long id2);


}
