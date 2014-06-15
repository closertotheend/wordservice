package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordEntity;
import com.wordservice.mvc.model.WordTuple;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordTupleRepository extends GraphRepository<WordTuple> {

}
