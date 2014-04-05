package com.wordservice.mvc.model;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

@RelationshipEntity(type = "IS_FOLLOWED_BY")
class WordRelationship {
    @GraphId
    private Long id;

    @StartNode
    private WordEntity startWord;

    @EndNode
    private WordEntity secondWord;

    private long popularity;

    private WordRelationship() {
    }

    public WordRelationship(WordEntity startWord, WordEntity secondWord) {
        this.startWord = startWord;
        this.secondWord = secondWord;
    }

    public WordEntity getStartWord() {
        return startWord;
    }

    public WordEntity getSecondWord() {
        return secondWord;
    }

    public long getPopularity() {
        return popularity;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

    public void incrementPopularity() {
        this.popularity++;
    }
}