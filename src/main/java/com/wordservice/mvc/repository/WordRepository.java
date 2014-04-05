package com.wordservice.mvc.repository;

import com.wordservice.mvc.model.WordEntity;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface WordRepository extends GraphRepository<WordEntity> {


}
