package com.wordservice.mvc.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "IS_FOLLOWED_BY")
public class WordRelationship {
    public static final String relationshipType = "IS_FOLLOWED_BY";

    @GraphId
    private Long id;

    @JsonIgnore
    @StartNode
    private WordEntity startWord;

    @JsonIgnore
    @EndNode
    private WordEntity secondWord;

    private long popularity;

    public WordRelationship() {
    }

    public WordRelationship(WordEntity startWord, WordEntity secondWord) {
        this.startWord = startWord;
        this.secondWord = secondWord;
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

    public WordEntity getStartWord() {
        return startWord;
    }

    public WordEntity getSecondWord() {
        return secondWord;
    }

    public void setSecondWord(WordEntity secondWord) {
        this.secondWord = secondWord;
    }

    public void setStartWord(WordEntity startWord) {
        this.startWord = startWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordRelationship that = (WordRelationship) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


}