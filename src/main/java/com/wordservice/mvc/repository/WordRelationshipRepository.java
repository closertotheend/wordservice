package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Service
public interface WordRelationshipRepository extends GraphRepository<WordRelationship> {

    @Query("START wordEntity1=node({first}), wordEntity2=node({second}) MATCH wordEntity1-[r:IS_FOLLOWED_BY_TUPLE]->wordEntity2 " +
            "WHERE r.third={third} AND r.fourth={fourth} RETURN r ORDER BY r.popularity DESC LIMIT 20")
    WordRelationship getTuple(@Param("first") long first, @Param("second") long second, @Param("third") long third, @Param("fourth") long fourth);

    @Query("START wordEntity1=node({first}), wordEntity2=node({second}) MATCH wordEntity1-[r:IS_FOLLOWED_BY_TUPLE]->wordEntity2 " +
            "WHERE r.third={third} RETURN DISTINCT r ORDER BY r.popularity DESC LIMIT 20")
    Set<WordRelationship> getTuple(@Param("first") long first, @Param("second") long second, @Param("third") long third);

}
