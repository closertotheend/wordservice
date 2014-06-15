package com.wordservice.mvc.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class WordTriTuple {
    @GraphId
    private Long id;

    private Long firstWordRelationshipId;
    private Long secondWordRelationshipId;
    private Long thirdWordRelationshipId;
    private Long popularity = 0l;

    public WordTriTuple(Long firstWordRelationshipId, Long secondWordRelationshipId, Long thirdWordRelationshipId) {
        this.firstWordRelationshipId = firstWordRelationshipId;
        this.secondWordRelationshipId = secondWordRelationshipId;
        this.thirdWordRelationshipId = thirdWordRelationshipId;
    }

    public WordTriTuple() {
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

    public Long getThirdWordRelationshipId() {
        return thirdWordRelationshipId;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    public void setThirdWordRelationshipId(Long thirdWordRelationshipId) {
        this.thirdWordRelationshipId = thirdWordRelationshipId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordTriTuple wordTuple = (WordTriTuple) o;

        if (id != null ? !id.equals(wordTuple.id) : wordTuple.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WordTriTuple{" +
                "id=" + id +
                ", firstWordRelationshipId=" + firstWordRelationshipId +
                ", secondWordRelationshipId=" + secondWordRelationshipId +
                ", thirdWordRelationshipId=" + thirdWordRelationshipId +
                ", popularity=" + popularity +
                '}';
    }
}
