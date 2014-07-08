package com.wordservice.mvc.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class WordTuple {
    @GraphId
    private Long id;

    @Indexed
    private Long fWordRelationshipId;

    @Indexed
    private Long secondWordRelationshipId;

    private Long popularity = 0l;

    public WordTuple(Long fWordRelationshipId, Long secondWordRelationshipId) {
        this.fWordRelationshipId = fWordRelationshipId;
        this.secondWordRelationshipId = secondWordRelationshipId;
    }

    public WordTuple() {
    }

    public Long incrementPopularity(){
        this.popularity++;
        return popularity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getfWordRelationshipId() {
        return fWordRelationshipId;
    }

    public void setfWordRelationshipId(Long fWordRelationshipId) {
        this.fWordRelationshipId = fWordRelationshipId;
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

        return !(id != null ? !id.equals(wordTuple.id) : wordTuple.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WordTuple{" +
                "id=" + id +
                ", fWordRelationshipId=" + fWordRelationshipId +
                ", secondWordRelationshipId=" + secondWordRelationshipId +
                ", popularity=" + popularity +
                '}';
    }
}
