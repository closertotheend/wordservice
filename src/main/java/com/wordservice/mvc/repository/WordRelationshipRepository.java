package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordRelationshipTuple;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@Service
public interface WordRelationshipRepository extends GraphRepository<WordRelationshipTuple> {

//    /**
//     * Uses indexes, but sometimes does not work, also falls if word(any other sign is executed)word
//     * Does not respect case, because of that returns list
//     * */
//    List<WordEntity> findByWord(String word);
//
//    @Query("START wordEntity=node:word(word={word1}) WHERE has(wordEntity.word) AND wordEntity.word = {word1}" +
//            "RETURN wordEntity")
//    WordEntity findByWordOptimized(@Param("word1") String word1);
//
//    @Query("START wordEntity=node:word(word={word}) " +
//            "MATCH wordEntity-[r:IS_FOLLOWED_BY_TUPLE]->otherWord" +
//            " RETURN otherWord ORDER BY r.popularity DESC LIMIT 10")
//    Set<WordEntity> getTop10WordsAfter(@Param("word") String word);
//
//    @Query("START wordEntity=node:word(word={word1}), wordEntity2=node:word(word={word2}) " +
//            "MATCH wordEntity-[r:IS_FOLLOWED_BY_TUPLE]->wordEntity2-[r2:IS_FOLLOWED_BY_TUPLE]->otherWord" +
//            " RETURN otherWord ORDER BY r.popularity, r2.popularity DESC LIMIT 10")
//    Set<WordEntity> getTop10WordsAfter(@Param("word1") String word1, @Param("word2") String word2);
//
//    @Query("START wordEntity= node(*) WHERE has(wordEntity.word) AND wordEntity.word =~ {word1} " +
//            "RETURN wordEntity ORDER BY wordEntity.popularity DESC LIMIT 10")
//    List<WordEntity> findByWordRegexOrderByPopularity(@Param("word1") String word1);
//
//    @Query("START wordEntity= node(*) WHERE has(wordEntity.word) AND wordEntity.word = {word1}" +
//            "RETURN wordEntity")
//    WordEntity findByWordWithoutFastIndex(@Param("word1") String word1);


    @Query("START wordEntity1=node({first}), wordEntity2=node({second}) MATCH wordEntity1-[r:IS_FOLLOWED_BY_TUPLE]->wordEntity2 " +
            "WHERE r.third={third} AND r.fourth={fourth} RETURN r ORDER BY r.popularity DESC LIMIT 20")
    WordRelationshipTuple getTuple(@Param("first") long first, @Param("second") long second, @Param("third") long third, @Param("fourth") long fourth);

    @Query("START wordEntity1=node({first}), wordEntity2=node({second}) MATCH wordEntity1-[r:IS_FOLLOWED_BY_TUPLE]->wordEntity2 " +
            "WHERE r.third={third} RETURN DISTINCT r ORDER BY r.popularity DESC LIMIT 20")
    Set<WordRelationshipTuple> getTuple(@Param("first") long first, @Param("second") long second, @Param("third") long third);

}
