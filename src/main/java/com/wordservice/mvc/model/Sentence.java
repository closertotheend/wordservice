package com.wordservice.mvc.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.support.index.IndexType;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class Sentence {
    @GraphId
    private Long id;

    @Indexed(indexName = "sentenceIndex",indexType = IndexType.FULLTEXT)
    @GraphProperty
    private List<Long> wordRelationships = new ArrayList<>();

    // GETTERS SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getWordRelationships() {
        return wordRelationships;
    }

    public void setWordRelationships(List<Long> wordRelationships) {
        this.wordRelationships = wordRelationships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sentence sentence = (Sentence) o;

        return !(id != null ? !id.equals(sentence.id) : sentence.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "id=" + id +
                ", wordRelationships=" + wordRelationships +
                '}';
    }
}
