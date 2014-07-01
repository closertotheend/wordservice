package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordTriTuple;
import com.wordservice.mvc.model.WordTuple;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

public interface WordTriTupleRepository extends GraphRepository<WordTriTuple> {

    @Query("START wordTuple= node(*) WHERE has(wordTuple.thirdWordRelationshipId) " +
            "AND wordTuple.firstWordRelationshipId = {id1} AND wordTuple.secondWordRelationshipId = {id2} " +
            "AND wordTuple.thirdWordRelationshipId = {id3}" +
            "RETURN wordTuple ORDER BY wordTuple.popularity DESC LIMIT 10")
    WordTriTuple getWithRelationShipIds(@Param("id1") Long id1, @Param("id2") Long id2, @Param("id3") Long id3);

}
