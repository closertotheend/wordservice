package com.wordservice.mvc.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.neo4j.annotation.*;

@RelationshipEntity(type = "IS_FOLLOWED_BY_TUPLE")
public class WordRelationshipTuple {
    public static final String relationshipType = "IS_FOLLOWED_BY_TUPLE";

    @GraphId
    private Long id;

    @JsonIgnore
    @StartNode
    private WordEntity first;

    @JsonIgnore
    @EndNode
    private WordEntity second;

    private long third;

    private long fourth;

    private long popularity = 1;

    public WordRelationshipTuple() {
    }

    public WordRelationshipTuple(WordEntity first, WordEntity second, WordEntity third, WordEntity fourth) {
        this.first = first;
        this.second = second;
        this.third = third.getId();
        this.fourth = fourth.getId();
    }

    public void incrementPopularity() {
        this.popularity++;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPopularity() {
        return popularity;
    }

    public WordEntity getFirst() {
        return first;
    }

    public WordEntity getSecond() {
        return second;
    }

    public void setSecond(WordEntity second) {
        this.second = second;
    }

    public void setFirst(WordEntity first) {
        this.first = first;
    }

    public static String getRelationshipType() {
        return relationshipType;
    }

    public long getThird() {
        return third;
    }

    public void setThird(long third) {
        this.third = third;
    }

    public long getFourth() {
        return fourth;
    }

    public void setFourth(long fourth) {
        this.fourth = fourth;
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