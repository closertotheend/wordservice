package com.wordservice.mvc.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import scala.annotation.target.getter;

@NodeEntity
public class WordTuple {
    @GraphId
    private Long id;

    private Long firstWordRelationshipId;
    private Long secondWordRelationshipId;
    private Long popularity = 0l;

    public WordTuple(Long firstWordRelationshipId, Long secondWordRelationshipId) {
        this.firstWordRelationshipId = firstWordRelationshipId;
        this.secondWordRelationshipId = secondWordRelationshipId;
    }

    public WordTuple() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFirstWordRelationshipId() {
        return firstWordRelationshipId;
    }

    public void setFirstWordRelationshipId(Long firstWordRelationshipId) {
        this.firstWordRelationshipId = firstWordRelationshipId;
    }

    public Long getSecondWordRelationshipId() {
        return secondWordRelationshipId;
    }

    public void setSecondWordRelationshipId(Long secondWordRelationshipId) {
        this.secondWordRelationshipId = secondWordRelationshipId;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordTuple wordTuple = (WordTuple) o;

        if (id != null ? !id.equals(wordTuple.id) : wordTuple.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WordTuple{" +
                "id=" + id +
                ", firstWordRelationshipId=" + firstWordRelationshipId +
                ", secondWordRelationshipId=" + secondWordRelationshipId +
                ", popularity=" + popularity +
                '}';
    }
}
