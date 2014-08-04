package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface WordRepository extends GraphRepository<WordEntity> {

    /**
     * Uses indexes, but sometimes does not work
     * */
    List<WordEntity> findByWord(String word);

    @Query("START wordEntity=node:word(word={word}) " +
            "MATCH wordEntity-[r:IS_FOLLOWED_BY]->otherWord" +
            " RETURN otherWord ORDER BY r.popularity DESC LIMIT 10")
    List<WordEntity> getTop10WordsAfter(@Param("word") String word);

    @Query("START wordEntity=node:word(word={word1}), wordEntity2=node:word(word={word2}) " +
            "MATCH wordEntity-[r:IS_FOLLOWED_BY]->wordEntity2-[r2:IS_FOLLOWED_BY]->otherWord" +
            " RETURN otherWord ORDER BY r.popularity, r2.popularity DESC LIMIT 10")
    List<WordEntity> getTop10WordsAfter(@Param("word1") String word1, @Param("word2") String word2);

    @Query("START wordEntity= node(*) WHERE has(wordEntity.word) AND wordEntity.word =~ {word1}" +
            "RETURN wordEntity ORDER BY wordEntity.popularity DESC LIMIT 10")
    List<WordEntity> findByWordRegexOrderByPopularity(@Param("word1") String word1);

}
