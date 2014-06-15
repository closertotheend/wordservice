package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordTriTuple;
import com.wordservice.mvc.model.WordTuple;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface WordTriTupleRepository extends GraphRepository<WordTriTuple> {

}
