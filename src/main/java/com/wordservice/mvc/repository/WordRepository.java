package com.wordservice.mvc.repository;

import com.wordservice.mvc.examples.Product;
import com.wordservice.mvc.model.WordEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.CypherDslRepository;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends GraphRepository<WordEntity>{
    WordEntity findByWord(String word);

    @Query("START wordEntity=node:word(word={word}) " +
            "MATCH wordEntity-[r:IS_FOLLOWED_BY]->otherWord" +
            " RETURN otherWord ORDER BY r.popularity DESC")
    List<WordEntity> getTop10WordsAfter(@Param("word") String word);
}
