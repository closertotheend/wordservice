package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends GraphRepository<WordEntity> {
    List<WordEntity> findByWord(String word);

    @Query("START wordEntity=node:word(word={word}) " +
            "MATCH wordEntity-[r:IS_FOLLOWED_BY]->otherWord" +
            " RETURN otherWord ORDER BY r.popularity DESC")
    List<WordEntity> getTop10WordsAfter(@Param("word") String word);

    @Query("START wordEntity=node:word(word={word1}), wordEntity2=node:word(word={word2}) " +
            "MATCH wordEntity-[r:IS_FOLLOWED_BY]->wordEntity2-[r2:IS_FOLLOWED_BY]->otherWord" +
            " RETURN otherWord ORDER BY r2.popularity DESC")
    List<WordEntity> getTop10WordsAfter(@Param("word1") String word1, @Param("word2") String word2);

    List<WordEntity> findByWordContaining(String word);

    List<WordEntity> findByWordStartingWith(String word);
}
