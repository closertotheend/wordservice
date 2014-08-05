package com.wordservice.mvc.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.neo4j.annotation.*;

@RelationshipEntity(type = "IS_FOLLOWED_BY_TUPLE")
public class WordRelationshipTuple {
    public static final String relationshipType = "IS_FOLLOWED_BY_TUPLE";

    @GraphId
    private Long id;

    private long first;

    private long second;

    @JsonIgnore
    @StartNode
    private WordEntity prelast;

    @JsonIgnore
    @EndNode
    private WordEntity last;

    private long popularity;

    public WordRelationshipTuple() {
    }

    public WordRelationshipTuple(WordEntity first, WordEntity second, WordEntity prelast, WordEntity last) {
        this.first = first.getId();
        this.second = second.getId();
        this.prelast = prelast;
        this.last = last;
    }

    public void incrementPopularity() {
        this.popularity++;
    }

    // GETTERS SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPopularity() {
        return popularity;
    }

    public WordEntity getPrelast() {
        return prelast;
    }

    public WordEntity getLast() {
        return last;
    }

    public void setLast(WordEntity last) {
        this.last = last;
    }

    public void setPrelast(WordEntity prelast) {
        this.prelast = prelast;
    }

    public static String getRelationshipType() {
        return relationshipType;
    }

    public long getFirst() {
        return first;
    }

    public void setFirst(long first) {
        this.first = first;
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordRelationshipTuple that = (WordRelationshipTuple) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}